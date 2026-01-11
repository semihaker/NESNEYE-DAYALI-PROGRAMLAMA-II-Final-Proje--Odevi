package com.smartlibraryplus.entity;

import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Table(name = "loans")
public class Loan {
    @Id
    private Long id;

    private LocalDate borrowDate;
    private LocalDate returnDate;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToOne
    @JoinColumn(name = "book_id", unique = true)
    private Book book;

    public Loan() {
    }

    public Loan(Book book) {
        this.book = book;
        this.borrowDate = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                ", studentId=" + (student != null ? student.getId() : null) +
                ", bookId=" + (book != null ? book.getId() : null) +
                '}';
    }
}
