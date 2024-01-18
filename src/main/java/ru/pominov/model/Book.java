package ru.pominov.model;

import java.util.Objects;

public class Book {

    private final Integer id;
    private final String author;
    private final String title;
    private final String publishDate;
    private final String description;

    public Book(Integer id, String author, String title, String publishDate, String description) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.publishDate = publishDate;
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", publishDate='" + publishDate + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(author, book.author) && Objects.equals(title, book.title) && Objects.equals(publishDate, book.publishDate) && Objects.equals(description, book.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, title, publishDate, description);
    }
}
