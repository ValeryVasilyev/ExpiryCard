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
        if (login == null || login.equals("") || login.contains(" ")) {
            String errorText = "Введите логин без пробелов";
            model.addAttribute("errorText", errorText);
            return "sign-inWithErrors";
        }
        Customer customer = new Customer(login);

        // Если нет введённого логина в БД, то создаём нового кастомера
        if (customerRepository.findByLogin(login) == null) {
            customerRepository.save(customer);
        } else {
            customer = customerRepository.findByLogin(login);
        }
        return "redirect:/" + customer.getId() + "/cards";
    }

}
