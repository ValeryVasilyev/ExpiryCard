package com.example.demo.repo;

import com.example.demo.models.Card;
import com.example.demo.models.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CardRepository extends CrudRepository<Card, Long> {

    Iterable<Card> findAllByCustomerId(@Param("customerId") Long customerId);

    Card findByCardNumber(@Param("cardNumber") Long cardNumber);

    void deleteCardById(@Param("id") Long id);

    Card findCardById(@Param("id") Long id);
}
