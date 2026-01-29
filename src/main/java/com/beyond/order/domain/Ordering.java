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
public class Ordering {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT), nullable = false)
    private Member member;
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.ORDERED;
    @Builder.Default
    private LocalDateTime createdTime =  LocalDateTime.now();


}
