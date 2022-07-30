package ru.netology;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

class CardDeliveryTest {

    @BeforeEach
    void openWebBank() {
        open("http://localhost:7777/");

    }

    @Test
    void testDelivery() {
        Configuration.timeout = 15;

        $("[data-test-id=city] input").val("Москва");
        $x("//input[@type='tel']").val("02.08.2022");
        $x("//input[@name='name']").val("Иван Петров-Водкин");
        $x("//input[@name='phone']").val("+79001231212");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
    }

}