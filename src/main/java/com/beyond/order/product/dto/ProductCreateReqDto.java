package com.beyond.order.product.dto;

import com.beyond.order.member.domain.Member;
import com.beyond.order.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductCreateReqDto {
    private String name;
    private int price;
    private String category;
    private int stockQuantity;
    private MultipartFile productImage;

    public Product toEntity(Member member) {
        return Product.builder()
                .name(this.name)
                .price(this.price)
                .category(this.category)
                .stockQuantity(this.stockQuantity)
                .member(member)
                .build();

    }
}
