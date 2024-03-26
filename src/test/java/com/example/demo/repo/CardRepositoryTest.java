package com.example.demo.repo;

import com.example.demo.models.Card;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CardRepositoryTest {

    @Autowired
    private CardRepository cardRepository;

    @Test
    void findAllByCustomerId() {
        Long customerId = 1L;
        Iterable<Card> cards = cardRepository.findAllByCustomerId(customerId);
        for (Card c : cards) {
            Assertions.assertEquals(customerId, c.getCustomerId());
        }
    }

    @Test
    void findCardById() {
        Card card = createCard();
        Assertions.assertEquals(card, cardRepository.findCardById(card.getId()));
    }

    @Test
    void deleteCardById() {
        Card card = createCard();
        cardRepository.deleteById(card.getId());
        Assertions.assertNull(cardRepository.findCardById(card.getId()));
    }

    private Card createCard() {
        Long customerId = 1L;
        Long cardNumber = 1L;
        LocalDateTime expiredAt = LocalDateTime.now();
        Card card = new Card(customerId, cardNumber, expiredAt);
        cardRepository.save(card);
        return card;
    }
}