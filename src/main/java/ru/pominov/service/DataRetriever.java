package ru.pominov.service;

import ru.pominov.config.AppLogger;
import ru.pominov.model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataRetriever {

    private static final Logger LOGGER = AppLogger.getLogger();

    public List<Book> retrieveData(Connection connection) {
        List<Book> bookList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM books");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String author = resultSet.getString("author");
                String title = resultSet.getString("title");
                String publishDate = resultSet.getString("publish_date");
                String description = resultSet.getString("description");

                Book book = new Book(id, author, title, publishDate, description);
                bookList.add(book);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to retrieve data from the database", e);
        }

        return bookList;
    }
}
