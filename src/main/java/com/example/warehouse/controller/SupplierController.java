package com.example.warehouse.controller;

import com.example.warehouse.entity.Input;
import com.example.warehouse.entity.Supplier;
import com.example.warehouse.repository.InputRepository;
import com.example.warehouse.repository.SupplierRepository;
import com.example.warehouse.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    SupplierRepository supplierRepository;
    @Autowired
    SupplierService supplierService;
    @Autowired
    InputRepository inputRepository;

    @GetMapping
    public String getSupplierPage(Model model) {
        model.addAttribute("list", supplierRepository.findAllByActiveTrue());
        return "supplier/supplier";
    }

    @GetMapping("/add")
    public String getSaveSupplier() {return "supplier/supplier-add";}

    @PostMapping("/add")
    public String saveSupplier(@ModelAttribute Supplier supplier) {
        supplierRepository.save(supplier);
        return "redirect:/supplier";
    }

    @GetMapping("/delete/{id}") //1 45 24 90
    public String delete(@PathVariable Integer id) {
        Optional<Input> inputBySupplier_id = inputRepository.findInputBySupplier_Id(id);
        if (inputBySupplier_id.isEmpty()) {
            Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
            if (optionalSupplier.isEmpty()) {
                return "redirect:/supplier";
            }
            Supplier supplier = optionalSupplier.get();
            supplier.setActive(false);
            supplierRepository.save(supplier);
        }
        return "redirect:/supplier";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Integer id) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
        if(optionalSupplier.isEmpty()) return "supplier/supplier";
        model.addAttribute("supplier", optionalSupplier.get());
        return "supplier/supplier-edit";
    }

    @PostMapping("/edit/{id}")
    public String editSupplier(@ModelAttribute Supplier supplier) {
        supplierRepository.save(supplier);
        return "redirect:/supplier";
    }
}
