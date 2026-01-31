package ru.netology.bankauthorization.data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.Locale;

// Datahelper - помощник данных, возвращает запрашиваемые данные.
public class DataHelper {

    // Конструктор DataHelper объявлен приватным и пустым.
    // Чтобы нельзя было создать методы класса извне, этот способ обеспечивает безопасность данных.
    // У конструктора такое же название, как и у класса.
    // Данный конструктор нужен просто пустым для обеспечения безопасности данных.
    private DataHelper() {

    }

    // Настройка библиотеки Faker, которая будет возвращать значения на "en" языке
    private static Faker faker = new Faker(new Locale("en"));

    // Метод возвращающий валидного пользователя
    public static Authorization getUserInfo() {

        return new Authorization("vasya", "qwerty123");
    }

    // Метод генерирующий рандомный логин
    public static String getRandomLogin () {

        return faker.name().username();
    }

    // Метод генерирующий рандомный пароль
    public static String getRandomPassword () {

        return faker.internet().password(12, 14, true, true, true);
    }

    // Метод генерирующий рандомного пользователя
    public static Authorization generateRandomUser() {

        return new Authorization(getRandomLogin (), getRandomPassword ());
    }

    // Метод генерирующий рандомный код верификации
    public static VerificationСode getRandomCode() {

        return new VerificationСode (faker.numerify("######"));
    }

    // Аннтоация Value от Lombok, упаковыавает данные в объекты.
    // К примеру, класс Authorization будет представлять единый объект, который хранит данные login и password
    // Также аннотация @Value хороша тем, что создаваемый класс Authorization объявляется неизменяемым («immutable»)
    // Данные класс можно вызывать в других классах через геттеры getLogin(); // Вернет Login и getPassword(); // Вернет Password
    @Value
    public static class Authorization {
        String login;
        String password;
    }


    //  Даёт много удобных операций, автоматически генерируя методы для работы с полями.
    @Data
    // Позволяет легко создавать объекты без конкретных данных.
    @NoArgsConstructor
    //  Помогает создать объекты сразу с заданием нужных значений.
    @AllArgsConstructor
    public static class VerificationСode {
        String code;
    }

}