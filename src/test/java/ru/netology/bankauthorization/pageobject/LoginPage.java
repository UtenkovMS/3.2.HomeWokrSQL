package ru.netology.bankauthorization.pageobject;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.bankauthorization.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private final SelenideElement erorNotification = $("[data-test-id='error-notification'] [class=notification__content]");

    // Проверка видимости текста об ошибке в качестве аргумента передаем значение (String expectedText)
    // Означающее, что метод будет ожидать в качестве аргумента текст об ошибке
    public void erorMassage (String expectedText) {

        erorNotification.shouldHave(Condition.text(expectedText))
                .shouldBe(Condition.visible, Duration.ofSeconds(7));
    }

    // Метод авторизация пользователя
    // Для того чтобы метод принимал данные о пользователе, укажем параметр (DataHelper.Authorization info)

    public VerificationPage authorizationUser(DataHelper.Authorization info) {

        authorization(info);

        return new VerificationPage();
    }

    public void authorization (DataHelper.Authorization info){

        $("[data-test-id='login'] input").setValue(info.getLogin());
        $("[data-test-id='password'] input").setValue(info.getPassword());
        $("[data-test-id='action-login']").click();

    }

}
