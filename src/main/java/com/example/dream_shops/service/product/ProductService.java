package com.example.dream_shops.service.product;

import com.example.dream_shops.dto.ImageDto;
import com.example.dream_shops.dto.ProductDto;
import com.example.dream_shops.exception.ALreadyExceptsException;
import com.example.dream_shops.exception.ProductNotFoundException;
import com.example.dream_shops.model.Category;
import com.example.dream_shops.model.Image;
import com.example.dream_shops.model.Product;
import com.example.dream_shops.repository.CategoryRepository;
import com.example.dream_shops.repository.ImageRepository;
import com.example.dream_shops.repository.ProductRepository;
import com.example.dream_shops.request.AddProductRequest;
import com.example.dream_shops.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private  final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
   private final ImageRepository imageRepository;


    @Override
    public Product addProduct(AddProductRequest request) {
        //check if the category is found in the DB
        //If yes ,set it as the new product category
        //if no ,then save it as a new category
        //then set it as the new product category

       if ((productExists(request.getName(),request.getBrand()))) {
           throw new ALreadyExceptsException
       (request.getBrand()+" "+request.getName()+" "+
               "already exists,You may update this product instead!");
       }
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }

    private boolean productExists(String name,String brand){
        return productRepository.existsByNameAndBrand(name,brand);
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
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
      return productRepository.findById(productId)
              .map(exixtingProduct->updateExistingProduct(exixtingProduct,request))
              .map(productRepository::save)
              .orElseThrow(()->new ProductNotFoundException("Product not found"));

    }
    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request){
     existingProduct.setName(request.getName());
     existingProduct.setBrand(request.getBrand());
     existingProduct.setPrice(request.getPrice());
     existingProduct.setInventory(request.getInventory());
     existingProduct.setDescription(request.getDescription());

     Category category=categoryRepository.findByName(request.getCategory().getName());
     existingProduct.setCategory(category);
     return existingProduct;
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
    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        return products.stream().map(this::convertToDto).toList();

    }
@Override
public ProductDto convertToDto(Product product) {
       ProductDto productDto = modelMapper.map(product, ProductDto.class);
       List<Image> images=imageRepository.findByProductId(product.getId());
       List<ImageDto> imageDtos= images.stream()
               .map(image -> modelMapper.map(image,ImageDto.class))
               .toList();
       productDto.setImages(imageDtos);
       return productDto;


    }
}
