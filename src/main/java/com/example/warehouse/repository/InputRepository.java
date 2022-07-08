package com.example.warehouse.repository;

import com.example.warehouse.entity.Input;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InputRepository extends JpaRepository<Input, Integer> {
    Optional<Input> findInputBySupplier_Id(Integer id);

    Optional<Input> findInputByCurrency_Id(Integer id);
}
