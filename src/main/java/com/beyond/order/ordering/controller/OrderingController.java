package com.beyond.order.ordering.controller;

import com.beyond.order.ordering.dto.OrderingCreateReqDto;
import com.beyond.order.ordering.dto.OrderingListResDto;
import com.beyond.order.ordering.dto.OrderingMyListResDto;
import com.beyond.order.ordering.service.OrderingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordering")
public class OrderingController {
    private final OrderingService orderingService;

    @Autowired
    public OrderingController(OrderingService orderingService) {
        this.orderingService = orderingService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createOrder(@RequestBody List<OrderingCreateReqDto> dto, @AuthenticationPrincipal String principal) {
        Long orderId = orderingService.createOrder(dto, principal);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderId);
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> orderingList() {
        List<OrderingListResDto> orderList = orderingService.orderingList();
        return ResponseEntity.status(HttpStatus.OK).body(orderList);
    }

    @GetMapping("/myorders")
//    여긴 인가 제거해야함
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> myOrderList(@AuthenticationPrincipal String principal) {
       List<OrderingMyListResDto> myOrderList = orderingService.myOrderList(principal);
        return ResponseEntity.status(HttpStatus.CREATED).body(myOrderList);
    }







}
