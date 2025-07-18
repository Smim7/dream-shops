package com.example.dream_shops.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        private String brand;
        private String image;
        private BigDecimal price;
        private int inventory;    //To see product is available or not
        private String description;

        @ManyToOne(cascade  =CascadeType.ALL)
        @JoinColumn(name="category_id")
        private Category category;

        @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
        private List<Image> images;

    }
