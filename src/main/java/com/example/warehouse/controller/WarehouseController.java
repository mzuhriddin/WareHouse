package com.example.warehouse.controller;

import com.example.warehouse.entity.Input;
import com.example.warehouse.entity.Warehouse;
import com.example.warehouse.repository.InputRepository;
import com.example.warehouse.repository.WarehouseRepository;
import com.example.warehouse.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/warehouse")
public class WarehouseController {
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    WarehouseService warehouseService;
    @Autowired
    InputRepository inputRepository;

    @GetMapping
    public String getWarehousePage(Model model) {
        model.addAttribute("list", warehouseRepository.findAllByActiveTrue());
        return "warehouse/warehouse";
    }

    @GetMapping("/add")
    public String getSaveWarehouse() {return "warehouse/warehouse-add";}

    @PostMapping("/add")
    public String saveWarehouse(@ModelAttribute Warehouse warehouse) {
        warehouseService.add(warehouse);
        return "redirect:/warehouse";
    }

    @GetMapping("/delete/{id}") //1 45 24 90
    public String delete(@PathVariable Integer id) {
            Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(id);

            if (optionalWarehouse.isEmpty()) {
                return "redirect:/warehouse";
            }
            Warehouse warehouse = optionalWarehouse.get();
            warehouse.setActive(false);
            warehouseRepository.save(warehouse);
        return "redirect:/warehouse";
        }



    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Integer id) {
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(id);
        if(optionalWarehouse.isEmpty()) return "warehouse/warehouse";
        model.addAttribute("warehouse", optionalWarehouse.get());
        return "warehouse/warehouse-edit";
    }

    @PostMapping("/edit/{id}")
    public String editWarehouse(@ModelAttribute Warehouse warehouse) {
        warehouseService.add(warehouse);
        return "redirect:/warehouse";
    }
}
