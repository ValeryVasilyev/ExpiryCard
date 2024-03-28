package ui;

import com.example.demo.models.Card;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.microsoft.playwright.options.WaitForSelectorState.VISIBLE;


public class PageObject {
    private final Page page;
    private final String url = "http://localhost:8081/";

    public PageObject(Page page) {
        this.page = page;
    }

    public void signIn() {
        page.navigate(url);
        page.locator("input").fill("admin");
        page.locator("button").click();
    }

    public Card createValidCard() {
        Locator button = page.locator("button:has-text(\"Создать новую карту\")");
        button.click();
        Card card = new Card();
        Long cardNumber = new Random().nextLong(1000, 9999);
        card.setCardNumber(cardNumber);
        page.locator("input[name='cardNumber']").fill(String.valueOf(cardNumber));
        page.locator("//button[text()='Создать карту']").click();
        return card;
    }

    public List<Card> getCardList() {
        Locator firstCard = page.locator("tbody tr").first();
        firstCard.waitFor();
        Locator cards = page.locator("tbody tr");
        List<String> tableContent = cards.allTextContents();
        List<Card> cardList = new ArrayList<>();
        for (String card : tableContent) {
            Card newCard = new Card();
            String[] split = card.split("\n");
            newCard.setId(Long.valueOf(split[1].trim()));
            newCard.setCardNumber(Long.valueOf(split[2].trim()));
            cardList.add(newCard);
        }
        return cardList;
    }

    public String deleteCard(int cardCount) {
        int randomIndex = new Random().nextInt(0, cardCount);
        Locator deleteButton = page.locator("tbody button").nth(randomIndex);
        String cardId = deleteButton.getAttribute("value");
        deleteButton.click();
        return cardId;
    }
}
