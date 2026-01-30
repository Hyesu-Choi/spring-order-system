package com.beyond.order.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.ORDERED;
    @Builder.Default
    private LocalDateTime createdTime =  LocalDateTime.now();

    @OneToMany(mappedBy = "ordering", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderingDetails> orderingDetails = new ArrayList<>();  // 캐스캐이딩할 때 초기화안하면 null exception 터짐.


    public void addOrderingDetails(OrderingDetails details) {
        orderingDetails.add(details);
        details.setOrder(this);
    }

    public static Ordering toEntity(Member member) {
        return Ordering.builder()
                .member(member)
                .build();
    }


}
