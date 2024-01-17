package ru.pominov.model;

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
}
