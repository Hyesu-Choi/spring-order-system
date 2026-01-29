package com.beyond.order.dto.product;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

public class ProductCreateReqDto {
    private String name;
    private int price;
    private String category;
    private int stockQuantity;
    private MultipartFile productImage;
}
