package ru.pominov;

import ru.pominov.config.AppLogger;
import ru.pominov.config.JdbcConnection;
import ru.pominov.model.Book;
import ru.pominov.service.DataImporter;
import ru.pominov.service.DataRetriever;
import ru.pominov.service.TableCreator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = AppLogger.getLogger();

    public static void main(String[] args) {
        JdbcConnection jdbcConnection = new JdbcConnection("jdbc:h2:mem:xmltest", "root", "root");
        DataImporter dataImporter = new DataImporter();
        DataRetriever dataRetriever = new DataRetriever();
        TableCreator tableCreator = new TableCreator();

        try (Connection connection = jdbcConnection.getConnection()) {
            // Создание таблицы 'books'
            tableCreator.createTable(connection);

            // Парсинг из xml-файла и импорт в БД
            dataImporter.importData(connection, "books.xml");

            // Парсинг из xml-файла и импорт в БД (новый файл, содержащий данные, уже имеющиеся в БД (для проверки игнорирования дубликатов на уровне БД))
            dataImporter.importData(connection, "new-books.xml");

            // Вытягиваем добавленные данные из таблицы 'books'
            List<Book> importedBooks = dataRetriever.retrieveData(connection);
            System.out.println("Добавленные данные в БД:");
            importedBooks.forEach(System.out::println);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "An error occurred during database operation", e);
        }
    }
}