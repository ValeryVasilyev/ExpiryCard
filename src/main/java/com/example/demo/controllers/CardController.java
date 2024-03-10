package com.example.demo.controllers;

import com.example.demo.models.Card;
import com.example.demo.repo.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


@Controller
public class CardController {

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    static DateTimeFormatter ruFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm").withLocale(new Locale("ru")); // Русский формат
    private String errorText = "";
    private String isCollapsed = "";
    private LocalDateTime setExpiredAt = LocalDateTime.now();

    @Autowired
    private CardRepository cardRepository;

    @GetMapping("/{id}/cards")
    public String getCards(Model model, @PathVariable(value = "id") Long customerId) {
        model = getModel(model, customerId); // Наполняем модель аттрибутами для html
        return "cards";
    }

    @PostMapping("/{id}/cards/createCard")
    public String createCard(@RequestParam(defaultValue = "0") Long cardNumber, @RequestParam(defaultValue = "") LocalDateTime expiredAt, @PathVariable(value = "id") Long customerId, Model model) {
        errorText = "";
        if (cardNumber <= 0) { // Обработка кейса, когда не передали номер карты
            errorText = "Номер карты должен быть больше 0";
            isCollapsed = "show";
            return "redirect:/" + customerId + "/cards";
        }
        Card card;
        // Если нет карты с введённым номером в БД, то создаём новую
        if (cardRepository.findByCardNumber(cardNumber) == null) {
            if (expiredAt == null) { // Кейс, если удалить значение из поля даты
                errorText = "Укажите дату окончания действия карты";
                isCollapsed = "show";
                return "redirect:/" + customerId + "/cards";
            }
            cardRepository.save(new Card(customerId, cardNumber, expiredAt));
            setExpiredAt = expiredAt;
            isCollapsed = "";
            return "redirect:/" + customerId + "/cards";
        } else { // Кейс ввода уже созданного номера карты в БД
            errorText = "Такой номер уже занят";
            isCollapsed = "show";
            return "redirect:/" + customerId + "/cards";
        }
    }

    /* Метод формирует данные для отображения на html-странице */
    public Model getModel(Model model, Long customerId) {
        model.addAttribute("defaultDate", LocalDateTime.now().format(formatter));
        model.addAttribute("customerId", customerId);
        model.addAttribute("errorText", errorText);
        model.addAttribute("isCollapsed", isCollapsed);
        model.addAttribute("expiredAt", setExpiredAt);
        clearParams();
        try { // блок для поимки исключения, когда таблица с картами отсутствует
            Iterable<Card> cards = cardRepository.findAllByCustomerId(customerId);
            for (Card card : cards) {
                // Перед отображение на фронте форматируем из формата yyyy-MM-dd'T'HH:mm в русский формат
                LocalDateTime createdAtDate = LocalDateTime.parse(card.getCreatedAt());
                LocalDateTime expiredAtDate = LocalDateTime.parse(card.getExpiredAt());
                card.setCreatedAt(createdAtDate.format(ruFormatter));
                card.setExpiredAt(expiredAtDate.format(ruFormatter));
            }
            model.addAttribute("cards", cards);
            return model;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return model;
        }
    }

    /* Метод "сбрасывает" значения аттрибутов к значениям по умолачанию */
    private void clearParams() {
        errorText = "";
        isCollapsed = "";
    }

    @PostMapping("/{id}/cards/deleteCard")
    public String deleteCard(@RequestParam Long cardId, @PathVariable(value = "id") Long customerId, Model model) {
        cardRepository.deleteById(cardId);
        return "redirect:/" + customerId + "/cards";
    }

}
