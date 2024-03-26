package com.example.demo.controllers;

import com.example.demo.models.Card;
import com.example.demo.repo.CardRepository;
import com.example.demo.service.CardService;
import com.example.demo.service.CardServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CardController.class)
class CardControllerTest {

    //@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mvc;

    @MockBean
    CardServiceImpl cardService;

    @MockBean
    private CardRepository cardRepository;

    Long customerId = 1L;

    @Test
    void getListOfCards() throws Exception {
        Iterable<Card> cards = createListOfCards();
        String url = "/" + customerId + "/cards";
        Mockito.when(cardService.findAllByCustomerId(customerId)).thenReturn(cards);
        MvcResult mvcResult = mvc.perform(get(url)).andReturn();
        Assertions.assertEquals(mvcResult.getModelAndView().getViewName(), "cards");
        Assertions.assertEquals(mvcResult.getModelAndView().getModel().get("cards"), cards);
    }

    @Test
    void createCard() throws Exception {
        String url = "/" + customerId + "/cards/createCard";
        Card card = new Card(customerId, 101L, LocalDateTime.now());
        MvcResult mvcResult = mvc.perform(post(url)
                                            .param("cardNumber", card.getCardNumber().toString())
                                            .param("expiredAt", card.getExpiredAt()))
                                                .andReturn();
        Assertions.assertEquals(mvcResult.getModelAndView().getViewName(), "redirect:/" + customerId + "/cards");
    }

    @Test
    void deleteCard() throws Exception {
        String url = "/" + customerId + "/cards/deleteCard";
        MvcResult mvcResult = mvc.perform(post(url)
                                            .param("cardId", "1"))
                                            .andReturn();
        Assertions.assertEquals(mvcResult.getModelAndView().getViewName(), "redirect:/" + customerId + "/cards");
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