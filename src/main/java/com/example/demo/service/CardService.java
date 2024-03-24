package com.example.demo.service;

import com.example.demo.models.Card;

import java.util.List;

public interface CardService {

    Iterable<Card> findAll();

    Iterable<Card> findAllByCustomerId(Long customerId);

    List<Card> save(Card card, Long customerId);

    List<Card> deleteById(Long id, Long customerId);
}
