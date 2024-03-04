package com.example.demo.controllers;

import com.example.demo.models.Card;
import com.example.demo.repo.CardRepository;
import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

//@RequestMapping(path="/{id}")
@Controller
public class CardController {

    @Autowired
    CardRepository cardRepository;

    @GetMapping("/{id}/cards")
    public String getCards(Model model, @PathVariable(value = "id") Long id) {
        return "cards";
    }
}
