package com.beyond.order.product.service;

import com.beyond.order.member.domain.Member;
import com.beyond.order.product.domain.Product;
import com.beyond.order.product.dto.ProductSearchDto;
import com.beyond.order.product.dto.ProductCreateReqDto;
import com.beyond.order.product.dto.ProductDetailRespDto;
import com.beyond.order.member.repository.MemberRepository;
import com.beyond.order.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final S3Client s3Client;
    @Value("${aws.s3.bucket1}")
    private String bucket;

    @Autowired
    public ProductService(ProductRepository productRepository, MemberRepository memberRepository, S3Client s3Client) {
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
        this.s3Client = s3Client;
    }

    public Long productCreate(ProductCreateReqDto dto, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다"));

        Product product = dto.toEntity(member);
        Product productDb = productRepository.save(product);

        MultipartFile productImage = dto.getProductImage();

        if (productImage != null) {
            String fileName = "product-" + product.getId() + "-productImage-" + productImage.getOriginalFilename();
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileName)
                    .contentType(productImage.getContentType())
                    .build();
            try {
                s3Client.putObject(request, RequestBody.fromBytes(productImage.getBytes()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String imgUrl = s3Client.utilities().getUrl(a -> a.bucket(bucket).key(fileName)).toExternalForm();
            product.updateProfileImageUrl(imgUrl);
        }

        return productDb.getId();
    }

    public ProductDetailRespDto productDetail(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("찾으시는 상품이 없습니다."));
        ProductDetailRespDto dto = ProductDetailRespDto.fromEntity(product);
        return dto;
    }

    public Page<ProductDetailRespDto> productList(
            Pageable pageable,
            ProductSearchDto searchDto
    ) {
        Specification<Product> specification = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchDto.getProductName() != null && !searchDto.getProductName().isBlank()) {
                predicates.add(
                        cb.like(root.get("name"), "%" + searchDto.getProductName() + "%")
                );
            }
            if (searchDto.getCategory() != null) {
                predicates.add(
                        cb.equal(root.get("category"), searchDto.getCategory())
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<Product> productPage = productRepository.findAll(specification, pageable);

        return productPage.map(ProductDetailRespDto::fromEntity);
    }


}
