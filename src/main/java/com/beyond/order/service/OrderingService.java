package com.beyond.order.service;

import com.beyond.order.domain.Member;
import com.beyond.order.domain.Ordering;
import com.beyond.order.domain.OrderingDetails;
import com.beyond.order.domain.Product;
import com.beyond.order.dto.ordering.OrderingCreateReqDto;
import com.beyond.order.dto.ordering.OrderingListResDto;
import com.beyond.order.dto.ordering.OrderingMyListResDto;
import com.beyond.order.repository.MemberRepository;
import com.beyond.order.repository.OrderingDetailRepository;
import com.beyond.order.repository.OrderingRepository;
import com.beyond.order.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderingService {
    private final OrderingRepository orderingRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final OrderingDetailRepository orderingDetailRepository;

    @Autowired
    public OrderingService(OrderingRepository orderingRepository, MemberRepository memberRepository, ProductRepository productRepository, OrderingDetailRepository orderingDetailRepository) {
        this.orderingRepository = orderingRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
        this.orderingDetailRepository = orderingDetailRepository;
    }

    public Long createOrder(List<OrderingCreateReqDto> dto, String principal) {
        Member member =  memberRepository.findByEmail(principal).orElseThrow(() -> new EntityNotFoundException("구매자 정보를 찾을 수 없습니다."));
        Ordering ordering = Ordering.toEntity(member);


//        캐스캐이딩 persist 사용하는게 더 좋음. ordering data 1개, orderDetail data n개

        for(OrderingCreateReqDto item : dto) {
            Product product = productRepository.findById(item.getProductId()).orElseThrow(() -> new EntityNotFoundException("상품 정보를 찾을 수 없습니다."));
            OrderingDetails orderDetail = OrderingDetails.builder()
                    .ordering(ordering)
                    .product(product)
                    .quantity(item.getProductCount())
                    .build();
            ordering.getOrderingDetails().add(orderDetail);
        }

        orderingRepository.save(ordering);
        return ordering.getId();
    }

    public List<OrderingListResDto> orderingList() {
//        List<Ordering> orders = orderingRepository.findAll();
//        return orders.stream().map(OrderingListResDto::fromEntity).toList();
        return orderingRepository.findAll().stream().map(OrderingListResDto::fromEntity).toList();
    }

    public List<OrderingMyListResDto> myOrderList(String principal) {
        Member member =  memberRepository.findByEmail(principal).orElseThrow(() -> new EntityNotFoundException("구매자 정보를 찾을 수 없습니다."));

//        List<Ordering> orderList =
//                orderingRepository.findByMemberId(member.getId());
//        return orderList.stream()
//                .map(OrderingMyListResDto::fromEntity)
//                .toList();

        return orderingRepository.findAllByMember(member).stream().map( o-> OrderingMyListResDto.fromEntity(o)).collect(Collectors.toList());
    }


}
