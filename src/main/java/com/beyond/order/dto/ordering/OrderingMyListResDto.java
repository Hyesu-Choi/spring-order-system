package com.beyond.order.dto.ordering;


import com.beyond.order.domain.OrderStatus;
import com.beyond.order.domain.Ordering;
import com.beyond.order.domain.OrderingDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
