package com.MicroservicePractice.ProductService.service.serviceImpl;

import com.MicroservicePractice.ProductService.entity.Product;
import com.MicroservicePractice.ProductService.exception.ProductServiceCustomException;
import com.MicroservicePractice.ProductService.model.ProductRequest;
import com.MicroservicePractice.ProductService.model.ProductResponse;
import com.MicroservicePractice.ProductService.repository.ProductRepository;
import com.MicroservicePractice.ProductService.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.beans.BeanUtils.*;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("Adding product...");
        System.out.println("productRequest::: " + productRequest);
        Product product
                = Product.builder()
                .productName(productRequest.getName())
                .quantity(productRequest.getQuantity())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);
        log.info("Product Created");
        return product.getProductId();
    }

    @Override
    public ProductResponse getProductById(long productId) {
        log.info("Getting the product for productId: {}", productId);

        Product product
                = productRepository.findById(productId)
                .orElseThrow(
                        () -> new ProductServiceCustomException("Product with given id not found", "PRODUCT_NOT_FOUND"));

        ProductResponse productResponse = new ProductResponse();
        copyProperties(product, productResponse);
        return productResponse;
    }
}
