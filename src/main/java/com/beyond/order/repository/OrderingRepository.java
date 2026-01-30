package com.beyond.order.repository;

import com.beyond.order.domain.Ordering;
import com.beyond.order.dto.ordering.OrderingCreateReqDto;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderingRepository extends JpaRepository<Ordering, Long> {
    @EntityGraph(attributePaths = {
            "member",
            "orderingDetails",
            "orderingDetails.product"
    })
    List<Ordering> findByMemberId(Long memberId);
}
