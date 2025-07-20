package com.example.dream_shops.dto;

import com.example.dream_shops.model.Category;
import com.example.dream_shops.model.Image;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {
        private Long id;
        private String name;
        private String brand;
        private String image;
        private BigDecimal price;
        private int inventory;    //To see product is available or not
        private String description;
        private Category category;
        private List<ImageDto> images;
}
