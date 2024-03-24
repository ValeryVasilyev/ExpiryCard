package com.example.demo.service;

import com.example.demo.models.Card;
import com.example.demo.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class ExpiryNotifier {

    @Autowired
    private CustomerRepository customerRepository;

    public void notify(Card card, Long customerId) {
        String message = createMessage(card, customerId);
        makeNotification(message);
    }

    public String createMessage(Card card, Long customerId) {
        String login = customerRepository.findById(customerId).get().getLogin();
        String message = "Уважаемый " + login + ", Ваша карта №" + card.getCardNumber() + " просрочена";
        return message;
    }

    public void makeNotification(String message) {
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
