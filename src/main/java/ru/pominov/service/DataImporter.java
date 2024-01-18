package ru.pominov.service;

import ru.pominov.config.AppLogger;
import ru.pominov.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataImporter {

    private static final Logger LOGGER = AppLogger.getLogger();

    public void importData(Connection connection, String xmlFilePath) {
        Set<Book> bookSet = XmlDataParser.parseXml(xmlFilePath);

        try {
            // Отключаем автоматическую фиксацию транзакций в БД
            connection.setAutoCommit(false);

            insertData(connection, bookSet);

            connection.commit();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Connection to database refused", e);
        }
    }

    /*
     * Метод для импорта данных в БД с учетом быстрой вставки
     */
    private void insertData(Connection connection, Set<Book> books) {
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

            int[] updatedRows;

            try {
                // Используем пакетную вставку и сохраняем количество обновленных строк в массив, который изначально возвращает метод
                updatedRows = statement.executeBatch();
            } catch (BatchUpdateException e) {
                int[] updateCounts = e.getUpdateCounts();

                List<Integer> successfulInserts = new ArrayList<>();

                for (int i = 0; i < updateCounts.length; i++) {
                    if (updateCounts[i] != Statement.EXECUTE_FAILED) {
                        // Операция вставки успешна, добавляем индекс в список
                        successfulInserts.add(i);
                    } else {
                        // Логируем предупреждение об ошибке вставки дубликата
                        LOGGER.log(Level.WARNING, "Duplicate data found: " + books.toArray()[i]);
                    }
                }

                // Преобразуем список количества успешных вставок в массив
                updatedRows = successfulInserts.stream().mapToInt(Integer::intValue).toArray();
            }

            // Логируем успешную вставку и количество элементов, добавленных в БД
            LOGGER.log(Level.INFO, "Insertion completed. Number of rows inserted: " + updatedRows.length);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to insert data to the database", e);
        }
    }
}
