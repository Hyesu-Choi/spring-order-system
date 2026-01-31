package com.beyond.order.ordering.dto;


import com.beyond.order.ordering.domain.OrderStatus;
import com.beyond.order.ordering.domain.Ordering;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderingMyListResDto {
    private Long id;
    private String memberEmail;
    private OrderStatus orderStatus;
    private List<OrderingDetailDto> orderDetails;

    public static OrderingMyListResDto fromEntity(Ordering ordering) {
        return OrderingMyListResDto.builder()
                .id(ordering.getId())
                .memberEmail(ordering.getMember().getEmail())
                .orderStatus(ordering.getOrderStatus())
                .orderDetails(
                        ordering.getOrderingDetails().stream()
                                .map(OrderingDetailDto::fromEntity)
                                .toList()
                )
                .build();

    }
}
