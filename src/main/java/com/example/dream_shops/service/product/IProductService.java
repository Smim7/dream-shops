package com.example.dream_shops.service.product;

import com.example.dream_shops.model.Product;

import java.util.List;

public interface IProductService {
    Product addProduct(Product product);
    Product getProductById(Long id);
    void updateProduct(Product product,Long productId);
    void deleteProduct(Long id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrand(String brand);
    List<Product>getProductByCategoryAndBrand(String category, String brand);
    List<Product>getProductByBrandAndName(String category, String name);
    Long countProductByBrandAndName(String brand, String name);


}
