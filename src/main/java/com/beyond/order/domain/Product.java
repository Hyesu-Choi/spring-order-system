package com.beyond.order.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
public class Product {
    @Id
    private Long id;
    private Member member;
    private String name;
    private int price;
    private String category;
    private int stockQuantity;
    private String imagePath;
    private LocalDateTime createdTime;


}
