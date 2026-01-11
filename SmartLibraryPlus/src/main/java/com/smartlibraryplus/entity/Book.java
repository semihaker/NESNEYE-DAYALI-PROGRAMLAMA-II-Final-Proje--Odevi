package com.smartlibraryplus.entity;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    private Long id;

    private String title;
    private String author;
    private int year;

    @Enumerated(EnumType.STRING)
    private BookStatus status;

    public Book() {
        this.status = BookStatus.AVAILABLE; // default
    }

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.status = BookStatus.AVAILABLE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + "'" +
                ", author='" + author + "'" +
                ", year=" + year +
                ", status=" + status +
                '}';
    }
}
