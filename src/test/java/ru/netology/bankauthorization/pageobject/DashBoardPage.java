package ru.netology.bankauthorization.pageobject;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class DashBoardPage {

    private final SelenideElement dashBoardElement = $("[data-test-id='dashboard']");

    // Проверку видимости страницы добавили в конструктор,
    // чтобы данная проверка происходила автоматически каждый раз при загрузке страницы.
    public DashBoardPage() {

        dashBoardElement.shouldBe(visible);
        dashBoardElement.shouldHave(text("Личный кабинет")).shouldBe(visible);
    }

}
