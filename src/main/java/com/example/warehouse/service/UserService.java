package com.example.warehouse.service;

import com.example.warehouse.dto.ApiResponse;
import com.example.warehouse.dto.UserDTO;
import com.example.warehouse.entity.User;
import com.example.warehouse.entity.Warehouse;
import com.example.warehouse.repository.UserRepository;
import com.example.warehouse.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

    public ApiResponse save(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setPassword(userDTO.getPassword());
        List<Warehouse> warehousesById = warehouseRepository.findAllById(userDTO.getWarehousesId());
        user.setWarehouseList(warehousesById);
        System.out.println(user);
        userRepository.save(user);
        return new ApiResponse("Saved!", true);
    }

    public ApiResponse edit(Integer id, UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()) return new ApiResponse("NOT FOUND", false);
        User user = optionalUser.get();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setPassword(user.getPassword());
        List<Warehouse> warehouseList = warehouseRepository.findAllById(userDTO.getWarehousesId());
        user.setWarehouseList(warehouseList);
        userRepository.save(user);
        return new ApiResponse("Edited!", true);
    }
}
