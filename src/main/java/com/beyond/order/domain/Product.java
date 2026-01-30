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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT), nullable = false)
    private Member member;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int price;
    private String category;
    @Column(nullable = false)
    private int stockQuantity;
    private String imagePath;
    @Builder.Default
    private LocalDateTime createdTime =  LocalDateTime.now();

    public void updateProfileImageUrl(String profileImageUrl) {
        this.imagePath = profileImageUrl;
    }

    public void decreaseStockQuantity(int quantity) {
        this.stockQuantity -= quantity;
    }


}
