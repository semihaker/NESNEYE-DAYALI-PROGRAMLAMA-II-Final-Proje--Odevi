package com.smartlibraryplus.dao;

import com.smartlibraryplus.entity.Book;
import com.smartlibraryplus.entity.BookStatus;
import com.smartlibraryplus.entity.Loan;
import com.smartlibraryplus.entity.Student;
import com.smartlibraryplus.util.HibernateUtil;
import java.time.LocalDate;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class LoanDao {

    public Loan save(Loan loan) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            if (loan.getId() == null) {
                Long max = (Long) session.createQuery("select max(id) from Loan").uniqueResult();
                loan.setId((max == null) ? 1L : max + 1L);
            }
            session.persist(loan);
            tx.commit();
            return loan;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public Loan update(Loan loan) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(loan);
            tx.commit();
            return loan;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public void delete(Loan loan) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.remove(session.contains(loan) ? loan : session.merge(loan));
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public Loan getById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Loan.class, id);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Loan> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Loan").list();
        }
    }

    public Loan createLoan(Long studentId, Long bookId) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Student student = session.get(Student.class, studentId);
            Book book = session.get(Book.class, bookId);

            if (student == null) {
                throw new IllegalArgumentException("Student not found: " + studentId);
            }
            if (book == null) {
                throw new IllegalArgumentException("Book not found: " + bookId);
            }

            if (book.getStatus() == BookStatus.BORROWED) {
                throw new IllegalStateException("Book is already borrowed: " + bookId);
            }

            // Update book status
            book.setStatus(BookStatus.BORROWED);
            session.merge(book);

            // Create loan
            Loan loan = new Loan(book);
            loan.setStudent(student);
            if (loan.getId() == null) {
                Long maxLoan = (Long) session.createQuery("select max(id) from Loan").uniqueResult();
                loan.setId((maxLoan == null) ? 1L : maxLoan + 1L);
            }
            session.persist(loan);

            tx.commit();
            return loan;
        } catch (RuntimeException e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public Loan returnLoan(Long loanId) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Loan loan = session.get(Loan.class, loanId);
            if (loan == null) {
                throw new IllegalArgumentException("Loan not found: " + loanId);
            }

            if (loan.getReturnDate() != null) {
                // already returned
                tx.commit();
                return loan;
            }

            loan.setReturnDate(LocalDate.now());
            Book book = loan.getBook();
            if (book != null) {
                book.setStatus(BookStatus.AVAILABLE);
                session.merge(book);
            }
            session.merge(loan);

            tx.commit();
            return loan;
        } catch (RuntimeException e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }
}
