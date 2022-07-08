package com.example.warehouse.service;

import com.example.warehouse.dto.ApiResponse;
import com.example.warehouse.dto.InputProductDTO;
import com.example.warehouse.dto.OutputDTO;
import com.example.warehouse.dto.OutputProductDTO;
import com.example.warehouse.entity.*;
import com.example.warehouse.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OutputService {

    @Autowired
    OutputRepository outputRepository;
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OutputProductRepository outputProductRepository;

    public ApiResponse save(OutputDTO outputDTO){
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(outputDTO.getWarehouseId());
        Optional<Currency> optionalCurrency = currencyRepository.findById(outputDTO.getCurrencyId());
        if (optionalWarehouse.isEmpty() || optionalCurrency.isEmpty()) return
                new ApiResponse("OutputService.save: Warehouse | Currency Not Found", false);
        Warehouse warehouse = optionalWarehouse.get();
        Currency currency = optionalCurrency.get();

        Output output = new Output();
        output.setWarehouse(warehouse);
        output.setCurrency(currency);
        output.setFactureNumber(outputDTO.getFactureNumber());

        for (OutputProductDTO outputProductDTO : outputDTO.getOutputProductDTOList()) {
            Optional<Product> optionalProduct = productRepository.findById(outputProductDTO.getProductId());
            if (optionalProduct.isEmpty()) return new ApiResponse("Akaajon bunaqa id yoq", false);
            OutputProduct outputProduct = new OutputProduct();
            outputProduct.setOutput(output);
            outputProduct.setProduct(optionalProduct.get());
            outputProduct.setAmount(outputProductDTO.getAmount());
            outputProduct.setPrice(outputProductDTO.getPrice());
            OutputProduct save = outputProductRepository.save(outputProduct);
            return new ApiResponse("Saved", true, save);
        }

        outputRepository.save(output);
        return new ApiResponse("OutputService.save: Saved", true);
    }
}
