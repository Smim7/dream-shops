package com.example.dream_shops.request;

import com.example.dream_shops.model.Category;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class AddProductRequest {
    private Long id;
    private String name;
    private String brand;
    private String image;
    private BigDecimal price;
    private int inventory;    //To see product is available or not
    private String description;
    private Category category;


}
