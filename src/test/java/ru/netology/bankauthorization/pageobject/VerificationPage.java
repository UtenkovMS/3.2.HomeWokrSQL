package ru.netology.bankauthorization.pageobject;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.bankauthorization.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {

    // Проверка видимости страницы добавили в конструктор,
    // Чтобы данная проверка происходила автоматически, каждый раз при загрузке страницы.
    public VerificationPage() {
        $("[data-test-id='code']").shouldBe(Condition.visible);
    }

    private final SelenideElement erorNotification = $("[data-test-id='error-notification'] [class='notification__content']");

    //Метод, который вводит вылидный код верификации через метод  verification(validCode)
    public DashBoardPage validVerification(String validCode) {

        verification(validCode); // вызываем внутренний метод verification в который передаем параметр (validCode)

        return new DashBoardPage(); // Возвращает страницу DashBoardPage

    }

    // Метод который вводит валидный код верификации и нажимает по кнопке.
    public void verification (String validCode){

        $("[data-test-id='code'] input").setValue(validCode);
        $("[data-test-id='action-verify']").click();
    };

    // Проверка видимости текста об ошибке в качестве аргумента передаем значение (String expectedText)
    // Означающее, что метод будет ожидать в качестве аргумента текст об ошибке
    public void erorMassage (String expectedText) {

        erorNotification.shouldHave(Condition.text(expectedText))
                .shouldBe(Condition.visible, Duration.ofSeconds(7));
    }

}
