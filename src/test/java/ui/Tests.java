package ui;

import com.example.demo.models.Card;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;


public class Tests extends BaseTest {

    private final String url = "http://37.230.192.102:8081/";

    @Test
    public void identificationTest() {
        page.navigate(url);
        Locator title = page.locator("h1");
        Assertions.assertEquals( "Введите логин", title.textContent());
        Locator input = page.locator("input");
        Assertions.assertEquals("Логин", input.getAttribute("placeholder"));
        Locator button = page.locator("button");
        Assertions.assertEquals("Войти или зарегистрироваться", button.textContent());
        input.fill("admin");
        button.click();
        Locator newPageTitle = page.locator("h3");
        Assertions.assertEquals("Ваши карты", newPageTitle.textContent());
    }

    @Test
    public void createCardTest() {
        PageObject pageObject = new PageObject(page);
        pageObject.signIn();
        Card createdCard = pageObject.createValidCard();
        List<Card> cardList = pageObject.getCardList();
        Assertions.assertTrue(cardList.stream().anyMatch(card -> card.getCardNumber().equals(createdCard.getCardNumber())));
    }

    @Test
    public void deleteCardTest() {
        PageObject pageObject = new PageObject(page);
        pageObject.signIn();
        Locator row = page.locator("tbody tr");
        row.first().waitFor();
        String cardId = pageObject.deleteCard(row.count());
        List<Card> cardList = pageObject.getCardList();
        Assertions.assertTrue(cardList.stream().noneMatch(card -> Objects.equals(card.getId(), Long.valueOf(cardId))));
    }
}
