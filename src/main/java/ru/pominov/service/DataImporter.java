package ru.pominov.service;

import ru.pominov.config.AppLogger;
import ru.pominov.model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataImporter {

    private static final Logger LOGGER = AppLogger.getLogger();

    public void importData(Connection connection, String xmlFilePath) {
        List<Book> bookList = XmlDataParser.parseXml(xmlFilePath);

        try {
            // Отключаем автоматическую фиксацию транзакций в БД
            connection.setAutoCommit(false);

            insertData(connection, bookList);

            connection.commit();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Connection to database refused", e);
        }
    }

    /*
     * Метод для импорта данных в БД с учетом быстрой вставки
     */
    private void insertData(Connection connection, List<Book> books) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO books (author, title, publish_date, description) VALUES (?, ?, ?, ?)")) {

            for (Book book : books) {
                statement.setString(1, book.getAuthor());
                statement.setString(2, book.getTitle());
                statement.setString(3, book.getPublishDate());
                statement.setString(4, book.getDescription());
                // Добавляем данные в пакет
                statement.addBatch();
            }

            // Используем пакетную вставку
            int[] updatedRows = statement.executeBatch();

            // Выводим количество обновленных записей в пакете для сверки с количеством спаршенных данных
            LOGGER.log(Level.INFO, "Insertion completed. Number of rows inserted: " + updatedRows.length);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to insert data to the database", e);
        }
    }
}
