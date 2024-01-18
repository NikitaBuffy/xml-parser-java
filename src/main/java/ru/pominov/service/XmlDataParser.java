package ru.pominov.service;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.pominov.config.AppLogger;
import ru.pominov.model.Book;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XmlDataParser {

    private static final Logger LOGGER = AppLogger.getLogger();

    public static Set<Book> parseXml(String filePath) {
        // Используем HashSet для обеспечения уникальных данных в процессе парсинга
        Set<Book> bookSet = new HashSet<>();

        try {
            File file = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(file);

            // Применяем нормализацию для приведения структуры дерева XML к стандартному виду
            document.getDocumentElement().normalize();

            // Извлекаем элементы из XML-документа по тегу 'book'
            NodeList nodeList = document.getElementsByTagName("book");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String author = element.getElementsByTagName("author").item(0).getTextContent().trim();
                    String title = element.getElementsByTagName("title").item(0).getTextContent().trim();
                    String publishDate = element.getElementsByTagName("publish_date").item(0).getTextContent().trim();
                    String description = element.getElementsByTagName("description").item(0).getTextContent().trim();

                    bookSet.add(new Book(null, author, title, publishDate, description));
                }
            }

            // Выводим количество спаршенных объектов для сверки
            LOGGER.log(Level.INFO, "Parsed from XML. Amount: " + bookSet.size() + ". Data: " + bookSet);
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "XML file not found", e);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            LOGGER.log(Level.SEVERE, "Error parsing XML", e);
        }

        return bookSet;
    }
}
