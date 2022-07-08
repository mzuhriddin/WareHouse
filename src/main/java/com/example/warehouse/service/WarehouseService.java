package com.example.warehouse.service;

import com.example.warehouse.dto.ApiResponse;
import com.example.warehouse.entity.Warehouse;
import com.example.warehouse.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarehouseService {
    @Autowired
    WarehouseRepository warehouseRepository;

    public ApiResponse add(Warehouse warehouse){
        Warehouse save = warehouseRepository.save(warehouse);
        return new ApiResponse("Saved", true, save);
    }
}
