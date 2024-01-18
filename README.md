# XML-парсер

Программа, импортирующая данные из xml-файла в БД. 

Требования и ограничения:
- Использовать чистую Java (без Spring и других фреймворков)

- Для импорта использовать JDBC. Учесть быструю вставку.

- Запрет на дубликаты. Они могут быть в исходном xml или уже присутствовать в БД. Реализация не должна пропускать дублирующие данные.

В качестве примера программа импортирует данные из файла `books.xml` в корне проекта и добавляет в созданную таблицу `books`. Затем импортируется еще один файл `new-books.xml`, который содержит одну из книг, уже добавленную ранее в БД, чтобы проверить на ограничение по добавлению дубликатов.


## Используемые технологии

Java 17, JDBC, Maven, H2


## Инструкция по запуску

```bash
  mvn clean install
```
```bash
  java -jar target/xml-parser-java-1.0-SNAPSHOT.jar
```

Либо запуск класса Main из IDEA.
