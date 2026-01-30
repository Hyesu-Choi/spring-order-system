package com.beyond.order.service;

import com.beyond.order.domain.OrderingDetails;
import com.beyond.order.domain.Product;
import com.beyond.order.repository.OrderingDetailRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OrderingDetailService {
    private final OrderingDetailRepository orderingDetailRepository;
    @Autowired
    public OrderingDetailService(OrderingDetailRepository orderingDetailRepository) {
        this.orderingDetailRepository = orderingDetailRepository;
    }

//    public void createOrderDetails(Product product, int productCount) {
//        orderingDetailRepository.save();
//    }




}
