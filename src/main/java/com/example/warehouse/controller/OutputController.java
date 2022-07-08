package com.example.warehouse.controller;

import com.example.warehouse.dto.ApiResponse;
import com.example.warehouse.dto.OutputDTO;
import com.example.warehouse.entity.OutputProduct;
import com.example.warehouse.entity.OutputProduct;
import com.example.warehouse.repository.*;
import com.example.warehouse.service.OutputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/output")
public class OutputController {

    @Autowired
    OutputRepository outputRepository;
    @Autowired
    OutputService outputService;
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OutputProductRepository outputProductRepository;

    @GetMapping
    public String getOutputPage(Model model){
        model.addAttribute("outputList", outputRepository.findAll());
        return "output/output";
    }

    @GetMapping("/add")
    public String getAddPage(Model model){
        model.addAttribute("productList", productRepository.findAllByActiveTrue());
        model.addAttribute("warehouseList", warehouseRepository.findAllByActiveTrue());
        model.addAttribute("currencyList", currencyRepository.findAllByActiveTrue());
        model.addAttribute("clientList", clientRepository.findAll());
        model.addAttribute("today", LocalDate.now().toString());
        return "output/output-add";
    }

    @PostMapping("/add")
    public String addOutput(@ModelAttribute OutputDTO outputDTO){
        outputService.save(outputDTO);
        return "redirect:/output";
    }

    @GetMapping("/delete/{id}")
    public String deleteOutput(@PathVariable Integer id){
        Optional<OutputProduct> optionalOutputProduct = outputProductRepository.findOutputProductByOutputId(id);
        if (optionalOutputProduct.isEmpty()) {
            outputRepository.deleteById(id);
        }
        return "redirect:/output";
    }
}
