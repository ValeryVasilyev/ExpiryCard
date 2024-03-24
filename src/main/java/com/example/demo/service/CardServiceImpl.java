package com.example.demo.service;

import com.example.demo.models.Card;
import com.example.demo.repo.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CardServiceImpl implements CardService{

    @Autowired
    CardRepository cardRepository;

    @Autowired
    ApplicationContext context;

    public Iterable<Card> findAll() {
        return cardRepository.findAll();
    }

    @Cacheable(value = "cards", key = "#customerId")
    public Iterable<Card> findAllByCustomerId(Long customerId) {
        return cardRepository.findAllByCustomerId(customerId);
    }

    @CachePut(value = "cards", key = "#customerId")
    public List<Card> save(Card card, Long customerId) {
        cardRepository.save(card);
        return (List<Card>) findAllByCustomerId(customerId);
    }

    @CachePut(value = "cards", key = "#customerId")
    public List<Card> deleteById(Long id, Long customerId) {
        List<Card> cardList = (List<Card>) findAllByCustomerId(customerId);
        cardList.remove(cardRepository.findCardById(id));
        cardRepository.deleteById(id);
        return cardList;
    }
}
