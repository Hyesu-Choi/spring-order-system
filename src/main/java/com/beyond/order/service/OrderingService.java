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

        for(OrderingCreateReqDto item : dto) {
            Product product = productRepository.findById(item.getProductId()).orElseThrow(() -> new EntityNotFoundException("상품 정보를 찾을 수 없습니다."));
            if(product.getStockQuantity() < item.getProductCount()) {
                throw new IllegalArgumentException("재고가 부족합니다.");
            }
            product.decreaseStockQuantity(item.getProductCount());

            OrderingDetails orderingDetails = OrderingDetails.save(product, item.getProductCount());
            ordering.addOrderingDetails(orderingDetails);
        }

        orderingRepository.save(ordering);
        return ordering.getId();
    }

    public List<OrderingListResDto> orderingList() {
        List<Ordering> orders = orderingRepository.findAll();
        return orders.stream().map(OrderingListResDto::fromEntity).toList();
    }

    public List<OrderingMyListResDto> myOrderList(String principal) {
        Member member =  memberRepository.findByEmail(principal).orElseThrow(() -> new EntityNotFoundException("구매자 정보를 찾을 수 없습니다."));
//        Ordering ordering = Ordering.toEntity(member);

        List<Ordering> orderList =
                orderingRepository.findByMemberId(member.getId());
        return orderList.stream()
                .map(OrderingMyListResDto::fromEntity)
                .toList();
    }


}
