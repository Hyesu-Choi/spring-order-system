package com.beyond.order.ordering.domain;

import com.beyond.order.common.domain.BaseTimeEntity;
import com.beyond.order.product.domain.Product;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
public class OrderingDetails extends BaseTimeEntity {
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

    public static OrderingDetails save(Product product, int quantity) {
        return OrderingDetails.builder()
                .product(product)
                .quantity(quantity)
                .build();
    }

    public void setOrder(Ordering ordering) {
        this.ordering = ordering;
    }

}
