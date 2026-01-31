package ru.netology.bankauthorization.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlHelper {

    // Класс QueryRunner является частью библиотеки Apache Commons DBUtils,
    // предназначенной для упрощения работы с базами данных в Java-приложениях
    // QueryRunner упрощает работу с SQL-запросами
    // query - переводится как запрос
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    private SqlHelper() {
    }

    // Класс Connection - интерфейс библиотеки JDBC (Java Database Connectivity)
    // используется для соединения с базой данных.
    // Параметр throws SQLException необходим для стабильности приложения,
    // если возникнут ошибки при подключении к базе данных, программа не завершится,
    // а выдаст сообщение об ошибке.
    private static Connection getConn() throws SQLException {

        //`DriverManager.getConnection()` — это метод Java для получения соединения с базой данных.
        // Данный метод устанавливает соединение с указанной базой данных.
        // Параметр db.url - задается в bild.gradle
        return DriverManager.getConnection(System.getProperty("db.url"), "app", "pass");
    }

    // Аннотация @SneakyThrows
    // Используется для подавления появления сообщений об исключениях.
    // Применяется, чтобы сделать код короче и чище.
    @SneakyThrows

    // Метод получает код верификации из базы данных и передает его в метод DataHelper.VerificationСode
    public static DataHelper.VerificationСode getVerificationCode () {

        // Запрос: выбери значение SELECT из столбца code из таблицы aut_codes,
        // упорядочив значения ORDER BY по столбцу created в порядке убывания DESC,
        // взяв одно значение LIMIT 1
        var codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        // Конструкция try нужна чтобы установить соединение с ресурсом, напр. с БД, и после закрыть его.
        // В скобках указывается адрес ресурса. В данном случае это метод getConn() который подключается к
        // адресу db.url в котором зашит порт БД SQL.
        try (var conn = getConn()) {

            // Здесь пишется тело запроса (query)
            // Метод query() класса QUERY_RUNNER для выполнения sql запросов
            // В скобках указываются аргументы conn для подключения к БД, codeSQL - команда sql запроса,
            // new BeanHandler<>(DataHelper.VerificationСode.class) - это  утилита, которая обрабатывает ответ запроса
            // и преобразует его в объект DataHelper.VerificationСode.class
            // В скобочках мы делаем ссылку на объект т.е. класс DataHelper объект VerificationСode
            // метод .query применяется для выборки и возврата данных.
            return QUERY_RUNNER.query(conn, codeSQL, new BeanHandler<>(DataHelper.VerificationСode.class));
        }
    }

    // Аннотация `@SneakyThrows`
    // Используется для подавления появления сообщений об исключениях.
    // Применяется, чтобы сделать код короче и чище.
    @SneakyThrows
    public static void cleanDataBase () {
        try (var conn = getConn()) {

            // Метод execute() предназначен для выполнения SQL-запросов,
            // которые не предполагают возвращения данных (результатов), но выполняют изменения в базе данных.
            QUERY_RUNNER.execute(conn, "DELETE FROM auth_codes" );
            QUERY_RUNNER.execute(conn, "DELETE FROM card_transactions");
            QUERY_RUNNER.execute(conn, "DELETE FROM cards");
            QUERY_RUNNER.execute(conn, "DELETE FROM users");
        }
    }

    @SneakyThrows
    public static void cleanAuthCodes () {
        try (var conn = getConn()) {

            // Метод execute() предназначен для выполнения SQL-запросов,
            // которые не предполагают возвращения данных (результатов), но выполняют изменения в базе данных.
            QUERY_RUNNER.execute(conn, "DELETE FROM auth_codes" );

        }
    }
}
