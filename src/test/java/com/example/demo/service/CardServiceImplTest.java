package com.example.demo.service;

import com.example.demo.models.Card;
import com.example.demo.repo.CardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardServiceImplTest {

    @InjectMocks
    CardServiceImpl service;

    @Mock
    CardRepository cardRepository;

    Long customerId = 1L;

    @Test
    void findAll() {
        Iterable<Card> cards = createListOfCards();
        when(cardRepository.findAll()).thenReturn(cards);
        Iterable<Card> result = service.findAll();
        assertEquals(cards, result);
    }

    @Test
    void findAllByCustomerId() {
        Iterable<Card> cards = createListOfCards();
        when(cardRepository.findAllByCustomerId(customerId)).thenReturn(cards);
        Iterable<Card> result = service.findAllByCustomerId(customerId);
        assertEquals(cards, result);
    }

    @Test
    void save() {
        Iterable<Card> cards = createListOfCards();
        Card card = new Card(1L, 10L, LocalDateTime.now());
        when(cardRepository.findAllByCustomerId(customerId)).thenReturn(cards);
        List<Card> result = service.save(card, customerId);
        assertEquals(cards, result);
    }

    @Test
    void deleteById() {
        Iterable<Card> cards = createListOfCards();
        when(cardRepository.findAllByCustomerId(customerId)).thenReturn(cards);
        List<Card> result = service.deleteById(1L, customerId);
        assertEquals(cards, result);
    }

    private Iterable<Card> createListOfCards() {
        Long cardNumber1 = 1L;
        Long cardNumber2 = 2L;
        Long cardNumber3 = 3L;
        LocalDateTime expiredAt = LocalDateTime.now();
        Card card1 = new Card(customerId, cardNumber1, expiredAt);
        Card card2 = new Card(customerId, cardNumber2, expiredAt.plusDays(1));
        Card card3 = new Card(customerId, cardNumber3, expiredAt.plusDays(2));
        card1.setId(1L);
        card2.setId(2L);
        card3.setId(3L);
        Iterable<Card> cards = Arrays.asList(card1, card2, card3);
        return cards;
    }
}