package com.beyond.order.ordering.repository;

import com.beyond.order.member.domain.Member;
import com.beyond.order.ordering.domain.Ordering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderingRepository extends JpaRepository<Ordering, Long> {
//    @EntityGraph(attributePaths = {
//            "member",
//            "orderingDetails",
//            "orderingDetails.product"
//    })
//    List<Ordering> findByMemberId(Long memberId);
    List<Ordering> findAllByMember(Member member);
}
