package com.smartlibraryplus;

import com.smartlibraryplus.dao.BookDao;
import com.smartlibraryplus.dao.LoanDao;
import com.smartlibraryplus.dao.StudentDao;
import com.smartlibraryplus.entity.Book;
import com.smartlibraryplus.entity.BookStatus;
import com.smartlibraryplus.entity.Loan;
import com.smartlibraryplus.entity.Student;
import static org.junit.Assert.*;
import org.junit.Test;

public class LoanIntegrationTest {

    @Test
    public void testLoanAndReturn() {
        BookDao bookDao = new BookDao();
        StudentDao studentDao = new StudentDao();
        LoanDao loanDao = new LoanDao();

        Book b = new Book("Test Book","Author",2024);
        bookDao.save(b);
        Student s = new Student("Test Student","CS");
        studentDao.save(s);

        Loan loan = loanDao.createLoan(s.getId(), b.getId());
        assertNotNull(loan);
        assertEquals(BookStatus.BORROWED, bookDao.getById(b.getId()).getStatus());

        Loan returned = loanDao.returnLoan(loan.getId());
        assertNotNull(returned.getReturnDate());
        assertEquals(BookStatus.AVAILABLE, bookDao.getById(b.getId()).getStatus());
    }
}
