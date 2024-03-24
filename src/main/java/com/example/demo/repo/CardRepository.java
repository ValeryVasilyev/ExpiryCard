package com.example.demo.repo;

import com.example.demo.models.Card;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends CrudRepository<Card, Long> {

    Iterable<Card> findAll();

    Iterable<Card> findAllByCustomerId(@Param("customerId") Long customerId);

    Card save(Card card);

    void deleteById(@Param("id") Long id);

    Card findCardById(Long id);
}
