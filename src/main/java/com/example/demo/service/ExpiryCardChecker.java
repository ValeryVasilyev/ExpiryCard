package com.example.demo.service;

import com.example.demo.models.Card;
import com.example.demo.repo.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExpiryCardChecker {

    @Autowired
    CardService cardService;

    @Autowired
    ExpiryNotifier expiryNotifier;

    @Autowired
    ApplicationContext context;

    /* Задача на выявление просроченных карт */
    @Scheduled(fixedRate = 60000) // Период проверки: 1 минута
    public Iterable<Card> checkCardExpiry() {
        Iterable<Card> allCards = cardService.findAll();
        allCards.forEach(card -> {
            if (!card.getIsExpired() && LocalDateTime.parse(card.getExpiredAt()).isBefore(LocalDateTime.now())) {
                card.setIsExpired(true);
                cardService.save(card, card.getCustomerId());
                expiryNotifier.notify(card, card.getCustomerId());
            }
        });
        return allCards;
    }

}
