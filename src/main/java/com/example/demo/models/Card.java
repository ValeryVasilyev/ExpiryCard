package com.example.demo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Card {

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");

    public Card(Long customerId, Long cardNumber, LocalDateTime expiredAt){
        this.customerId = customerId;
        this.cardNumber = cardNumber;
        this.createdAt = LocalDateTime.now().format(formatter); // Дату создания форматируем под читаемый формат
        this.expiredAt = formatFrontDate(expiredAt); // Дату окончания действия форматируем под читаемый формат
        this.isExpired = expiredAt.isBefore(LocalDateTime.now());
    }

    private String formatFrontDate(LocalDateTime expiredAt) {
        DateTimeFormatter frontFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String tempDate = expiredAt.format(frontFormat);
        LocalDateTime formattedDate = LocalDateTime.parse(tempDate);
        return formattedDate.format(formatter);
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
