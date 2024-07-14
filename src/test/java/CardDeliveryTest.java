import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {
    private String selectionDate(long days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void shouldBeFilledInSuccessfully() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Вологда");
        String dateOfMeeting = selectionDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(dateOfMeeting);
        $("[data-test-id='name'] input").setValue("Платон Сократов-Аристотелев");
        $("[data-test-id='phone'] input").setValue("+79001231221");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно забронирована на " + dateOfMeeting));
    }
}
