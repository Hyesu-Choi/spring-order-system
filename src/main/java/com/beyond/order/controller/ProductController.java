package com.beyond.order.controller;

import com.beyond.order.dto.member.MemberMyinfoResqDto;
import com.beyond.order.dto.ordering.ProductSearchDto;
import com.beyond.order.dto.product.ProductCreateReqDto;
import com.beyond.order.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<?> productCreate(@ModelAttribute ProductCreateReqDto dto, @RequestPart("productImage") MultipartFile productImage, @AuthenticationPrincipal String principal) {
        Long productId = productService.productCreate(dto, productImage, principal);
        return ResponseEntity.status(HttpStatus.CREATED).body(productId);
    }

    @GetMapping("/detail/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> productDetail(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.productDetail(id));
    }

    @GetMapping("/list")
    public ResponseEntity<?> productList(@PageableDefault(size = 10, page = 0) Pageable pageable, @ModelAttribute ProductSearchDto searchDto)  {
        return ResponseEntity.status(HttpStatus.OK).body(productService.productList(pageable, searchDto));
    }


}
