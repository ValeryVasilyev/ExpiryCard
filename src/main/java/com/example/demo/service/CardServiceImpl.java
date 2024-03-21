package com.example.demo.service;

import com.example.demo.models.Card;
import com.example.demo.repo.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CardServiceImpl implements CardService{

    @Autowired
    CardRepository cardRepository;

    @Cacheable(value = "cards", key = "#customerId")
    public Iterable<Card> findAllByCustomerId(Long customerId) {
        return cardRepository.findAllByCustomerId(customerId);
    }

    //@Override
    @CachePut(value = "cards", key = "#customerId")
    public List<Card> save(Card card, Long customerId) {
        List<Card> cardList = (List<Card>) findAllByCustomerId(customerId);
        cardList.add(cardRepository.save(card));
        return cardList;
    }

    @CachePut(value = "cards", key = "#customerId")
    public List<Card> saveAll(List<Card> cardList, Long customerId) {
        //List<Card> cardList = (List<Card>) findAllByCustomerId(customerId);
        return cardRepository.saveAll(cardList);
    }


    @CachePut(value = "cards", key = "#customerId")
    public List<Card> deleteById(Long id, Long customerId) {
        List<Card> cardList = (List<Card>) findAllByCustomerId(customerId);
        cardList.remove(cardRepository.findCardById(id));
        cardRepository.deleteById(id);
        return cardList;
    }
}
