package com.example.demo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Card {

    // Запись дат в БД совершаем по формату фронта
    static DateTimeFormatter frontFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"); // Формат даты в календаре на фронте

    // Конструктор для создания карты
    public Card(Long customerId, Long cardNumber, LocalDateTime expiredAt){
        this.customerId = customerId;
        this.cardNumber = cardNumber;
        this.createdAt = LocalDateTime.now().format(frontFormat);
        this.expiredAt = expiredAt.format(frontFormat);
        this.isExpired = expiredAt.isBefore(LocalDateTime.now());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private Long cardNumber;

    private Long customerId;

    private String createdAt, expiredAt;

    private Boolean isExpired;
}
