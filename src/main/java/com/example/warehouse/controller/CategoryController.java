package com.example.warehouse.controller;

import com.example.warehouse.dto.ApiResponse;
import com.example.warehouse.dto.CategoryDTO;
import com.example.warehouse.entity.Category;
import com.example.warehouse.repository.CategoryRepository;
import com.example.warehouse.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryService categoryService;

    @GetMapping
    public String getCategoryPage(Model model){
        model.addAttribute("categoryList", categoryRepository.findAllByActiveTrue());
        return "category/category";
    }

    @GetMapping("/add")
    public String categoryAddPage(Model model){
        model.addAttribute("categoryList", categoryRepository.findAllByActiveTrue());
        return "category/category-add";
    }

    @PostMapping("/add")
    public String addCategory(@ModelAttribute CategoryDTO categoryDTO){
        categoryService.save(categoryDTO);
        return "redirect:/category";
    }

    @GetMapping("/edit/{id}")
    public String categoryEditPage(@PathVariable Integer id, Model model){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) return "error/error";
        model.addAttribute("category", optionalCategory.get());
        model.addAttribute("categoryList", categoryRepository.findAllByActiveTrue());
        return "category/category-edit";
    }

    @PostMapping("/edit/{id}")
    public String editCategory(@PathVariable Integer id, @ModelAttribute CategoryDTO categoryDTO){
        System.out.println(categoryService.update(id, categoryDTO));
        return "redirect:/category";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Integer id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) return "error/error";
        Category category = optionalCategory.get();
        category.setActive(false);
        categoryRepository.save(category);
        return "redirect:/category";
    }

}
