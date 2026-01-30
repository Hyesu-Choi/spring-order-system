package com.beyond.order.repository;

import com.beyond.order.domain.OrderingDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderingDetailRepository extends JpaRepository<OrderingDetails, Long> {

    List<OrderingDetails> findAllById(Long  id);
}
