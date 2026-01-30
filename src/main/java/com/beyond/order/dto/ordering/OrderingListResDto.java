package com.beyond.order.dto.ordering;

import com.beyond.order.domain.Ordering;
import com.beyond.order.domain.OrderingDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

    //    이건 꼭 이해해야함!
    public static OrderingListResDto fromEntity(Ordering ordering) {
        List<OrderingDetailDto> orderDetailDtos = new ArrayList<>();
        for(OrderingDetails orderDetail :  ordering.getOrderingDetails()) {
            orderDetailDtos.add(OrderingDetailDto.fromEntity(orderDetail));
        }

        OrderingListResDto orderListDto =  OrderingListResDto.builder()
                .id(ordering.getId())
                .memberEmail(ordering.getMember().getEmail())
                .orderStatus(ordering.getOrderStatus().toString())
                .orderDetails(orderDetailDtos)
                .build();

        return orderListDto;

    }


}
