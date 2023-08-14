package com.skosarev.library.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Book {

    private int id;

    private int ownerId;

    @NotEmpty(message = "Book title should not be empty")
    @Size(min = 1, max = 100, message = "Book title should be between 1 and 100 characters")
    private String title;

    @NotEmpty(message = "Book author should not be empty")
    @Size(min = 5, max = 100, message = "Book author should be between 5 and 100 characters")
    private String author;

    @NotNull(message = "Book year should not be empty")
    @Min(value = 1500, message = "Year should be greater than 1500")
    private int year;

    public Book() {
    }

    public Book(int id, int ownerId, String title, String author, int year) {
        this.id = id;
        this.ownerId = ownerId;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
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

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", ownerId=" + ownerId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                '}';
    }
}
