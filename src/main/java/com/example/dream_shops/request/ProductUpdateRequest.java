package com.example.dream_shops.request;

import com.example.dream_shops.model.Category;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class ProductUpdateRequest {

        private Long id;
        private String name;
        private String brand;
        private String image;
        private BigDecimal price;
        private int inventory;    //To see product is available or not
        private String description;
        private Category category;

    }
