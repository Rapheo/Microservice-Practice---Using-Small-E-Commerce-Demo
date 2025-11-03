package com.MicroservicePractice.ProductService.service;

import com.MicroservicePractice.ProductService.model.ProductRequest;
import com.MicroservicePractice.ProductService.model.ProductResponse;

public interface ProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId);

    void reduceQuantity(long productId, long quantity);
}
