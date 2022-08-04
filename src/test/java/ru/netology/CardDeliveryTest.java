package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        String planDate = generateDate(6);

        $("[data-test-id=city] input").val("Владивосток");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@type='tel']").val(planDate);
        $x("//input[@name='name']").val("Иван Ласточкин");
        $x("//input[@name='phone']").val("+79991112233");
        $("[data-test-id=agreement]").click();
        $(".button.button_view_extra.button_size_m").click();
        $("[data-test-id=success-notification]")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + planDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);

    }

}