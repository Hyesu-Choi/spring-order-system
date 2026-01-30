package com.beyond.order.dto.ordering;

import com.beyond.order.domain.Ordering;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderingListResDto {
    private Long id;
    private String memberEmail;
    private String orderStatus;
    private List<OrderingDetailDto> orderDetails;

    public static OrderingListResDto fromEntity(Ordering ordering) {
        return OrderingListResDto.builder()
                .id(ordering.getId())
                .memberEmail(ordering.getMember().getEmail())
                .orderStatus(ordering.getOrderStatus().toString())
                .orderDetails( ordering.getOrderingDetails().stream()
                        .map(OrderingDetailDto::fromEntity)
                        .toList())
                .build();

    }


}
