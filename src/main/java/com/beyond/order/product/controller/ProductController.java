package com.beyond.order.product.controller;

import com.beyond.order.product.dto.ProductSearchDto;
import com.beyond.order.product.dto.ProductCreateReqDto;
import com.beyond.order.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> productCreate(@ModelAttribute ProductCreateReqDto dto, @AuthenticationPrincipal String principal) {
        Long productId = productService.productCreate(dto, principal);
        return ResponseEntity.status(HttpStatus.CREATED).body(productId);
    }

    @GetMapping("/detail/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> productDetail(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.productDetail(id));
    }

    @GetMapping("/list")
    public ResponseEntity<?> productList(Pageable pageable, ProductSearchDto searchDto)  {
//        Page객체로 받아서 body에 넣기

        return ResponseEntity.status(HttpStatus.OK).body(productService.productList(pageable, searchDto));
    }


}
