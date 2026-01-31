package ru.netology.bankauthorization.test;



import org.junit.jupiter.api.*;
import ru.netology.bankauthorization.data.DataHelper;
import ru.netology.bankauthorization.data.SqlHelper;
import ru.netology.bankauthorization.pageobject.LoginPage;
import ru.netology.bankauthorization.pageobject.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.bankauthorization.data.SqlHelper.cleanAuthCodes;
import static ru.netology.bankauthorization.data.SqlHelper.cleanDataBase;

public class AuthorizationTest {

    // Объявляем переменные на уровне класса, чтобы они были доступны в разных тестах из одного места
    LoginPage loginPage;
    VerificationPage verificationPage;

    // Объявляем переменную типа DataHelper.Authorization с именем authInfo.
    // Присваиваем этой переменной результат вызова метода getUserInfo(), который возвращает данные пользователя.
    DataHelper.Authorization authInfo = DataHelper.getUserInfo();
    // var authInfo = DataHelper.getUserInfo();

    //Методы, помеченные аннотациями @AfterAll, являются специальными методами,
    //которые предназначены для очистки ресурсов после завершения всех тестов в рамках класса

    // Аннотация: @AfterAll
    // Описание: Данный метод вызывается ровно один раз ПОСЛЕ выполнения ВСЕХ тестов в классе.
    // Представь, что в ходе тестов создается большое количество записей в базе данных.
    // После окончания всех тестов полезно удалить эти записи, чтобы очистить базу данных и подготовить её для следующего запуска.

    //Особенность: Всегда помечен как static, так как он запускается независимо от экземпляров класса.
    @AfterAll
    static void tearDownAll(){
        cleanDataBase();
    }

    //Методы, помеченные аннотациями @AfterEach применяются для очистки после каждого индивидуального теста.
    //После каждого теста важно убрать следы предыдущих испытаний, чтобы последующие тесты могли начать выполнение с чистого листа.
    @AfterEach
    void tearDown(){
        cleanAuthCodes();
    }

    // @BeforeEach - это аннотация в JUnit, означающая, что данный метод выполняется перед каждым тестом.
    @BeforeEach
    void setUp(){

        // Метод открывает страницу и сохраняет ссылку на объект типа LoginPage.
        loginPage = open("http://localhost:9999", LoginPage.class);
        var validUser = DataHelper.getUserInfo();
    }

    // Должен авторизировать валидного пользователя с валидным кодом верификации
    @Test
    @DisplayName("Should authorization valid user and valid verification code")
    void shouldBeAuthorizationValidUser (){

        var validUser = DataHelper.getUserInfo();
        var verificationPage = loginPage.authorizationUser(validUser);
        // code — это контейнер (экземпляр класса), содержащий внутри себя значение проверочного кода.
        var code = SqlHelper.getVerificationCode();
        // чтобы из code достать значение кода, мы применяем метод code.getCode() который вернет нам из БД значение кода
        verificationPage.verification(code.getCode());
    }

    // Должен авторизировать невалидного пользователя
    @Test
    @DisplayName("Should authorization invalid user")
    void shouldBeAuthorizationInValidUser (){

        var randomUser = DataHelper.generateRandomUser();
        loginPage.authorization(randomUser);
        loginPage.erorMassage("Неверно указан логин или пароль");
    }


    // Должен авторизировать валидного пользователя с невалидным кодом верификации
    @Test
    @DisplayName("Should authorization valid user and an invalid verification code")

    void shouldBeAuthorizationValidUserAndInvalidVerificationCode (){
        var validUser = DataHelper.getUserInfo();
        var code = DataHelper.getRandomCode();
        var verificationPage = loginPage.authorizationUser(validUser);
        verificationPage.verification(code.getCode());
        verificationPage.erorMassage("Неверно указан код! Попробуйте ещё раз.");
    }

}
