package com.example.warehouse.service;

import com.example.warehouse.dto.ApiResponse;
import com.example.warehouse.dto.InputDTO;
import com.example.warehouse.dto.InputProductDTO;
import com.example.warehouse.entity.*;
import com.example.warehouse.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InputService {
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

    public ApiResponse add(InputDTO inputDTO) {
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(inputDTO.getWarehouseId());
        Optional<Supplier> optionalSupplier = supplierRepository.findById(inputDTO.getSupplierId());
        Optional<Currency> optionalCurrency = currencyRepository.findById(inputDTO.getCurrencyId());
        if (optionalWarehouse.isEmpty() || optionalSupplier.isEmpty() || optionalCurrency.isEmpty()) return new
                ApiResponse("Akaajon bunaqa id yoq", false);
        Warehouse warehouse = optionalWarehouse.get();
        Supplier supplier = optionalSupplier.get();
        Currency currency = optionalCurrency.get();

        Input input = new Input();
        input.setDate(inputDTO.getDate());
        input.setWarehouse(warehouse);
        input.setSupplier(supplier);
        input.setCurrency(currency);
        input.setFactureNumber(inputDTO.getFactureNumber());
        Input save = inputRepository.save(input);

        for (InputProductDTO inputProductDTO : inputDTO.getInputProductDTOList()) {
            Optional<Product> optionalProduct = productRepository.findById(inputProductDTO.getProductId());
            if (optionalProduct.isEmpty()) return new ApiResponse("Akaajon bunaqa id yoq", false);
            InputProduct inputProduct = new InputProduct();
            inputProduct.setInput(save);
            inputProduct.setProduct(optionalProduct.get());
            inputProduct.setAmount(inputProductDTO.getAmount());
            inputProduct.setPrice(inputProductDTO.getPrice());
            inputProduct.setExpireDate(inputProductDTO.getExpireDate());
            inputProductRepository.save(inputProduct);
        }

        return new ApiResponse("Saved", true, save);
    }

    public ApiResponse update(InputDTO inputDTO, Integer id) {
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(inputDTO.getWarehouseId());
        Optional<Supplier> optionalSupplier = supplierRepository.findById(inputDTO.getSupplierId());
        Optional<Currency> optionalCurrency = currencyRepository.findById(inputDTO.getCurrencyId());
        Optional<Input> optionalInput = inputRepository.findById(id);
        if (optionalWarehouse.isEmpty() || optionalSupplier.isEmpty() || optionalCurrency.isEmpty() || optionalInput.isEmpty()) return new ApiResponse("Akaajon bunaqa id yoq", false);
        Warehouse warehouse = optionalWarehouse.get();
        Supplier supplier = optionalSupplier.get();
        Currency currency = optionalCurrency.get();

        Input input = optionalInput.get();
        input.setDate(inputDTO.getDate());
        input.setWarehouse(warehouse);
        input.setSupplier(supplier);
        input.setCurrency(currency);
        input.setFactureNumber(inputDTO.getFactureNumber());
        Input update = inputRepository.save(input);

        for (InputProductDTO inputProductDTO : inputDTO.getInputProductDTOList()) {
            Optional<Product> optionalProduct = productRepository.findById(inputProductDTO.getProductId());
            if (optionalProduct.isEmpty()) return new ApiResponse("Akaajon bunaqa id yoq", false);
            InputProduct inputProduct = new InputProduct();
            inputProduct.setInput(update);
            inputProduct.setProduct(optionalProduct.get());
            inputProduct.setAmount(inputProductDTO.getAmount());
            inputProduct.setPrice(inputProductDTO.getPrice());
            inputProduct.setExpireDate(inputProductDTO.getExpireDate());
            return new ApiResponse("Updated", true);
        }

        return new ApiResponse("Updated", true, update);
    }
}
