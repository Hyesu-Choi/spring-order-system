package com.beyond.order.ordering.dto;

import com.beyond.order.ordering.domain.OrderingDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderingDetailDto {
    private Long detailId;
    private String productName;
    private int productCount;

    public static OrderingDetailDto fromEntity(OrderingDetails details) {
        return OrderingDetailDto.builder()
                .detailId(details.getId())
                .productName(details.getProduct().getName())
                .productCount(details.getQuantity())
                .build();
    }

}
