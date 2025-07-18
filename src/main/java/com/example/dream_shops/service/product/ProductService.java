package com.example.dream_shops.service.product;

import com.example.dream_shops.exception.ProductNotFoundException;
import com.example.dream_shops.model.Category;
import com.example.dream_shops.model.Product;
import com.example.dream_shops.repository.CategoryRepository;
import com.example.dream_shops.repository.ProductRepository;
import com.example.dream_shops.request.AddProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private  final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    @Override
    public Product addProduct(AddProductRequest request) {
        //check if the category is found in the DB
        //If yes ,set it as the new product category
        //if no ,then save it as a new category
        //then set it as the new product category

        Category category= Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                                                 .orElseGet(()->{
               Category newcategory=new Category(request.getCategory().getName());
               return categoryRepository.save(newcategory);
                                                 });
        request.setCategory(category);

        return productRepository.save(createProduct(request,category));
    }
    private Product createProduct(AddProductRequest request, Category category) {
        return  new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
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
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete,
                        ()->{throw new ProductNotFoundException("Product not found");});

    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }
}
