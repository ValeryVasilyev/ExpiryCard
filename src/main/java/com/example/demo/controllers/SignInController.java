package com.example.demo.controllers;

import com.example.demo.models.Customer;

import com.example.demo.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLIntegrityConstraintViolationException;

@Controller
public class SignInController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/")
    public String signIn(Model model) {
        return "sign-in";
    }

    @PostMapping("/")
    public String getLogin(@RequestParam String login, Model model) {
        Customer customer = new Customer(login);
        try{
            customerRepository.save(customer);
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("login", login);
            return "AlreadyCreatedLogin";
        }
        return "redirect:/" + customer.getId() + "/cards";
    }

}
