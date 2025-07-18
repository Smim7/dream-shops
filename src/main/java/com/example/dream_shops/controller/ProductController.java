package com.example.dream_shops.controller;

import com.example.dream_shops.exception.ResourceNotFoundExceotion;
import com.example.dream_shops.model.Product;
import com.example.dream_shops.request.AddProductRequest;
import com.example.dream_shops.request.ProductUpdateRequest;
import com.example.dream_shops.response.ApiResponse;
import com.example.dream_shops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

@GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(new ApiResponse("success", products));
    }

@GetMapping("/product/{productid}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productid){
        try {
            Product product = productService.getProductById(productid);
            return ResponseEntity.ok(new ApiResponse("success", product));
        } catch (ResourceNotFoundExceotion e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

@PostMapping ("/add")
    public ResponseEntity<ApiResponse> addProduct( @RequestBody AddProductRequest product){
        try {
            Product theProduct = productService.addProduct(product);
            return   ResponseEntity.ok(new ApiResponse(" Add product success!",theProduct));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

@DeleteMapping("product/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProductById(@PathVariable Long productId){
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok(new ApiResponse("delete product success!",productId));
        } catch (ResourceNotFoundExceotion e) {
    return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

@PutMapping("/product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest request,
                                                         @PathVariable Long productId){
        try {
            Product theProduct = productService.updateProduct(request, productId);
            return  ResponseEntity.ok(new ApiResponse(" Update product success!",theProduct));
        } catch (ResourceNotFoundExceotion e) {
          return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

@GetMapping("/product/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName,@RequestParam String productName){
        try {
            List<Product> products = productService.getProductByBrandAndName(brandName,productName);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found",null));
            }
            return ResponseEntity.ok(new ApiResponse("success", products));
        } catch (Exception e) {
           return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }
@GetMapping("/products/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category, @RequestParam String brand){
        try {
            List<Product> products = productService.getProductByCategoryAndBrand(category, brand);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));
            }
            return  ResponseEntity.ok(new ApiResponse("success", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error", e.getMessage()));
        }
    }

@GetMapping("/products/by-brand")
    public ResponseEntity<ApiResponse> getProductByBrand(@RequestParam String brand){
        try {
            List<Product> products = productService.getProductsByBrand(brand);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));
            }
            return  ResponseEntity.ok(new ApiResponse("success", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error", e.getMessage()));
        }
    }

@GetMapping("/products/{category}/all/products")
    public ResponseEntity<ApiResponse> getProductByCategory(@PathVariable String category){
        try {
            List<Product> products = productService.getProductsByCategory(category);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));
            }
            return  ResponseEntity.ok(new ApiResponse("success", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error", e.getMessage()));
        }
    }

@GetMapping("/products/by-name")
    public ResponseEntity<ApiResponse> getProductByName(@RequestParam String name){
        try {
            List<Product> products = productService.getProductsByName(name);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found ", null));
            }
            return  ResponseEntity.ok(new ApiResponse("success", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error", e.getMessage()));
        }
    }

@GetMapping("/product/count/by-brant/and-name")
    public ResponseEntity<ApiResponse>countProductByBrandAndName(@RequestParam String brand, @RequestParam String Name){
    try {
        var count = productService.countProductByBrandAndName(brand,Name);
        return ResponseEntity.ok(new ApiResponse("product count", count));
    } catch (Exception e) {
       return ResponseEntity.ok(new ApiResponse(e.getMessage(),null));
    }

}



}
