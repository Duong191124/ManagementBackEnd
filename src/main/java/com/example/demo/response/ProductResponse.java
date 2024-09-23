package com.example.demo.response;

import com.example.demo.models.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {
    private int id;

    private String code;

    private String name;

    private String image;

    private String description;

    private Double price;

    private int status;

    private String categoryId;

    public static ProductResponse fromProductResponse(Product product){
        return ProductResponse
                .builder()
                .id(product.getId())
                .code(product.getCode())
                .name(product.getName())
                .image(product.getImage())
                .description(product.getDescription())
                .price(product.getPrice())
                .status(product.getStatus())
                .categoryId(product.getCategory().getName())
                .build();
    }
}
