package com.example.demo.service;

import com.example.demo.dto.ProductDTO;
import com.example.demo.models.Product;
import com.example.demo.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    Page<ProductResponse> getAll(PageRequest pageRequest, String keyword);

    Product save(ProductDTO productDTO) throws IOException;

    Product updateProductWithImage(Integer id, ProductDTO productDTO, MultipartFile file) throws Exception;

    Product getById(Integer id);

    Product saveProductWithImage(Integer id, MultipartFile file) throws IOException;

    void delete(Integer id);
}
