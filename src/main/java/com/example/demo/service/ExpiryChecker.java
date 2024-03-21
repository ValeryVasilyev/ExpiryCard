package com.example.demo.service;

import com.example.demo.models.Card;
import com.example.demo.repo.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class ExpiryChecker {

    @Autowired
    private CardService cardService;

    private LocalDateTime now = LocalDateTime.now();
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    static DateTimeFormatter ruFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm").withLocale(new Locale("ru")); // Русский формат

    public void defineExpiredCards(List<Card> cardList, Long customerId) {
        // Формируем список новых просроченных карт
        List<Card> newExpiredCardList = cardList
                .stream()
                    .filter(card -> !card.getIsExpired() &&
                            LocalDateTime.parse(card.getExpiredAt()).isBefore(LocalDateTime.now())).
                                toList();
        if (newExpiredCardList.size() > 1) {
            cardService.saveAll(newExpiredCardList, customerId);
        }
    }
}
