package com.example.demo.service.Impl;

import com.example.demo.dto.ProductDTO;
import com.example.demo.models.Category;
import com.example.demo.models.Product;
import com.example.demo.repository.CategoryRepo;
import com.example.demo.repository.ProductRepo;
import com.example.demo.response.ProductResponse;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private CloudinaryServiceImpl cloudinaryService;

    @Override
    public Page<ProductResponse> getAll(PageRequest pageRequest, String keyword) {
        Page<Product> productPage = productRepo.searchProduct(keyword, pageRequest);
        return productPage.map(ProductResponse::fromProductResponse);
    }

    @Override
    public Product save(ProductDTO productDTO){
        Category existingCategory = categoryRepo.findById(productDTO.getCategoryId()).orElse(null);
        String imageUrl = "";
        return productRepo.save(
                Product.builder()
                        .code(productDTO.getCode())
                        .name(productDTO.getName())
                        .description(productDTO.getDescription())
                        .price(productDTO.getPrice())
                        .status(1)
                        .image(imageUrl)
                        .category(existingCategory)
                        .build()
        );
    }

    @Override
    public Product updateProductWithImage(Integer id, ProductDTO productDTO, MultipartFile file) throws Exception {
        Product existingProduct = productRepo.findById(id).orElseThrow(() -> new Exception("ProductId can't found"));
        Category existingCategory = categoryRepo.findById(productDTO.getCategoryId()).orElseThrow(() -> new Exception("CategoryId can't found"));

        //Cập nhật thông tin sản phẩm
        existingProduct.setCode(productDTO.getCode());
        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setStatus(productDTO.getStatus());
        existingProduct.setCategory(existingCategory);

        //Nếu có ảnh mới, tải lên ảnh mới và cập nhật url
        if(file != null || !file.isEmpty()){

            //Nếu có ảnh cũ thì xóa ảnh cũ
            if(existingProduct.getImage() != null){
                String publicId = extractPublicId(existingProduct.getImage());
                cloudinaryService.deleteFile(publicId);
            }

            String newUrl = cloudinaryService.uploadFile(file);
            existingProduct.setImage(newUrl);
        }

        return productRepo.save(existingProduct);
    }

    @Override
    public Product getById(Integer id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    @Override
    public Product saveProductWithImage(Integer id, MultipartFile file) throws IOException {
        Optional<Product> existingProductOptional = productRepo.findById(id);
        if (existingProductOptional.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }

        Product existingProduct = existingProductOptional.get();
        String imageUrl = cloudinaryService.uploadFile(file);

        existingProduct.setImage(imageUrl);
        return productRepo.save(existingProduct);
    }

    @Override
    public void delete(Integer id) {
        productRepo.deleteById(id);
    }

    private String extractPublicId(String imageUrl) {
        //trích xuất publicId trong url của ảnh
        return imageUrl.split("/")[7].split("\\.")[0];
    }
}
