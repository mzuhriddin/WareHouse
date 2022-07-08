package com.example.warehouse.service;

import com.example.warehouse.dto.ApiResponse;
import com.example.warehouse.dto.CategoryDTO;
import com.example.warehouse.entity.Category;
import com.example.warehouse.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public ApiResponse save(CategoryDTO categoryDTO){
        Optional<Category> optionalCategory = categoryRepository.findById(categoryDTO.getCategoryId());
        if (optionalCategory.isEmpty()) return new ApiResponse("CategoryService.save: Category Not Found",false);

        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setCategory(optionalCategory.get());
        System.out.println(category);

        categoryRepository.save(category);
        return new ApiResponse("CategoryService.save: Saved", true);
    }

    public ApiResponse update(Integer id, CategoryDTO categoryDTO) {
        Optional<Category> optionalParent = categoryRepository.findById(categoryDTO.getCategoryId());
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty() | optionalParent.isEmpty()) return new
                ApiResponse("CategoryService.save: Category Not Found",false);

        Category edited = optionalCategory.get();
        edited.setName(categoryDTO.getName());
        edited.setCategory(optionalParent.get());
        System.out.println(edited);

        categoryRepository.save(edited);
        return new ApiResponse("CategoryService.save: Saved", true);

    }
}
