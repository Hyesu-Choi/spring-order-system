package com.beyond.order.domain;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT), nullable = false)
    private Ordering ordering;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT), nullable = false)
    private Product product;
    @Column(nullable = false)
    private int quantity;
    @Builder.Default
    private LocalDateTime createdTime =  LocalDateTime.now();

}
