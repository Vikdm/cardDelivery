package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

class CardDeliveryTest {

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeEach
    void openWebBank() {
        open("http://localhost:7777/");
    }

    @Test
    void testDelivery() {
        Configuration.timeout = 15;
        String planDate = generateDate(6);

        $("[data-test-id=city] input").val("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@type='tel']").val(planDate);
        $x("//input[@name='name']").val("Иван Петров-Водкин");
        $x("//input[@name='phone']").val("+79001231212");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void testDeliveryDate() {
        Configuration.timeout = 15;
        String planDate = generateDate(8);

        $("[data-test-id=city] input").val("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@type='tel']").val(planDate);
        $x("//input[@name='name']").val("Иван Петров-Водкин");
        $x("//input[@name='phone']").val("+79001231212");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
}