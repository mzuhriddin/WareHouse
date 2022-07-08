package com.example.warehouse.controller;

import com.example.warehouse.dto.InputDTO;
import com.example.warehouse.entity.InputProduct;
import com.example.warehouse.repository.*;
import com.example.warehouse.service.InputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/input")
public class InputController {

    @Autowired
    InputService inputService;
    @Autowired
    InputRepository inputRepository;
    @Autowired
    InputProductRepository inputProductRepository;
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    SupplierRepository supplierRepository;
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    ProductRepository productRepository;
    @GetMapping
    public String getInputPage(Model model) {
        model.addAttribute("list", inputRepository.findAll());
        return "input/input";
    }

    @GetMapping("/add")
    public String getSaveInput(Model model) {
        model.addAttribute("productList", productRepository.findAllByActiveTrue());
        model.addAttribute("warehouseList", warehouseRepository.findAllByActiveTrue());
        model.addAttribute("supplierList", supplierRepository.findAllByActiveTrue());
        model.addAttribute("currencyList", currencyRepository.findAllByActiveTrue());
        model.addAttribute("today", LocalDate.now().toString());
        return "input/input-add";
    }

    @PostMapping("/add")
    public String saveInput(@ModelAttribute InputDTO dto) {
        inputService.add(dto);
        return "redirect:/input";
    }

    @GetMapping("/delete/{id}") //1 45 24 90
    public String delete(@PathVariable Integer id) {
        Optional<InputProduct> optionalInputProduct = inputProductRepository.findInputProductByInputId(id);
        if (optionalInputProduct.isEmpty()) {
            inputRepository.deleteById(id);
        }
        return "redirect:/input";
    }


}
