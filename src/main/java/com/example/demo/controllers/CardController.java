package com.example.demo.controllers;

import com.example.demo.models.Card;
import com.example.demo.repo.CardRepository;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;

@RequestMapping(path="/{id}")
@Controller
public class CardController {

    // TODO: 1) Кнопка возврата на первую страницу авторизации 2) Удаление карты из БД

    //
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    @Autowired
    private CardRepository cardRepository;

    @GetMapping("/cards")
    public String getCards(Model model, @PathVariable(value = "id") Long customerId) {
        // По умолчанию в поле срока вставляем текущую дату и время
        model.addAttribute("defaultDate", LocalDateTime.now().format(formatter));
        model = getModel(model, customerId);
        return "cards";
    }

    @PostMapping("/cards")
    public String createCard(@RequestParam(defaultValue = "0") Long cardNumber, @RequestParam(defaultValue = "") LocalDateTime expiredAt, @PathVariable(value = "id") Long customerId, Model model) {
        String errorText;
        if (cardNumber <= 0) { // Обработка кейса, когда не передали номер карты
            errorText = "Номер карты должен быть больше 0";
            model.addAttribute("errorText", errorText);
            model.addAttribute("expiredAt", expiredAt);
            model = getModel(model, customerId);
            return "cardsWithErrors";
        }
        Card card;
        // Если нет карты с введённым номером в БД, то создаём новую
        if (cardRepository.findByCardNumber(cardNumber) == null) {
            if (expiredAt == null) { // Кейс, если удалить значение из поля даты
                errorText = "Введите дату окончания действия карты";
                model.addAttribute("errorText", errorText);
                expiredAt = LocalDateTime.now();
                model.addAttribute("expiredAt", expiredAt);
                model = getModel(model, customerId);
                return "cardsWithErrors";
            }
            cardRepository.save(new Card(customerId, cardNumber, expiredAt));
            return "redirect:/" + customerId + "/cards";
        } else { // Кейс ввода уже созданного номера карты в БД
            errorText = "Такой номер уже занят";
            model.addAttribute("errorText", errorText);
            model.addAttribute("expiredAt", expiredAt);
            model = getModel(model, customerId);
            return "cardsWithErrors";
        }
    }

    public Model getModel(Model model, Long customerId) {
        try { // блок для поимки исключения, когда таблица с картами отсутствует
            Iterable<Card> cards = cardRepository.findAllByCustomerId(customerId);
            model.addAttribute("cards", cards);
            return model;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return model;
        }
    }
}
