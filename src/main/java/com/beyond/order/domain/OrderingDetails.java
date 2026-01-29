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
public class OrderingDetails {
    @Id
    private Long id;
    private Ordering ordering;
    private Product product;
    private int quantity;
    private LocalDateTime createdTime;

}
