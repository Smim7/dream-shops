package com.example.dream_shops.service.product;

import com.example.dream_shops.exception.ProductNotFoundException;
import com.example.dream_shops.model.Product;
import com.example.dream_shops.repository.ProductRepository;

import java.util.List;

public class ProductService implements IProductService {
    private ProductRepository productRepository;
    @Override
    public Product addProduct(Product product) {
        return null;
    }

    @Override
    public Product getProductById(Long id) {
        return (Product) productRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException("Product not found"));
    }

    @Override
    public void updateProduct(Product product, Long productId) {

    }

    @Override
    public void deleteProduct(Long id) {

    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return List.of();
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return List.of();
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return List.of();
    }

    @Override
    public List<Product> getProductByCategoryAndBrand(String category, String brand) {
        return List.of();
    }

    @Override
    public List<Product> getProductByBrandAndName(String category, String name) {
        return List.of();
    }

    @Override
    public Long countProductByBrandAndName(String brand, String name) {
        return 0L;
    }
}
