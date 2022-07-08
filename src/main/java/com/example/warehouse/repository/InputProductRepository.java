package com.example.warehouse.repository;

import com.example.warehouse.entity.InputProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InputProductRepository extends JpaRepository<InputProduct, Integer> {
    Optional<InputProduct> findInputProductByInputId(Integer input_id);
}
