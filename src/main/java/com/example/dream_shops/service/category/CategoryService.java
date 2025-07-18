package com.example.dream_shops.service.category;

import com.example.dream_shops.exception.ALreadyExceptsException;
import com.example.dream_shops.exception.ResourceNotFound;
import com.example.dream_shops.model.Category;
import com.example.dream_shops.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category).filter(c->!categoryRepository.existsByName(c.getName()))
                .map(categoryRepository::save).orElseThrow(()->new ALreadyExceptsException(category.getName()+"already exists"));

    }

    @Override
    public Category updateCategory(Category category,Long id) {
        return Optional.ofNullable(getCategoryById(id)).map(oldcategory->{oldcategory.setName(category.getName());
                return categoryRepository.save(oldcategory);})
                .orElseThrow(()->new ResourceNotFound("category"));

    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.findById(id)
                .ifPresentOrElse(categoryRepository::delete,()->{throw new ResourceNotFound("Category not found");});

    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(()->new ResourceNotFound("Category not found"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }
}
