package com.example.demo.service;

import com.example.demo.models.Card;
import com.example.demo.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;

public class ExpiryNotifier {

    @Autowired
    private CustomerRepository customerRepository;

    public void createNotification(Card card) {
        Long customerId = card.getCustomerId();
        String login = customerRepository.findById(customerId).get().getLogin();
        String message = "Уважаемый " + login + ", Ваша карта №" + card.getCardNumber() + "просрочена";
        notify(message);
    }

    public void notify(String message) {
        try(FileWriter fw = new FileWriter("notifications.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
