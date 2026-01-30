package com.beyond.order.dto.ordering;

import com.beyond.order.domain.OrderingDetails;
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
