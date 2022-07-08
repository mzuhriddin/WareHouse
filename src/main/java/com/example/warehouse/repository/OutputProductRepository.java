package com.example.warehouse.repository;

import com.example.warehouse.entity.OutputProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OutputProductRepository extends JpaRepository<OutputProduct, Integer> {

    Optional<OutputProduct> findOutputProductByOutputId(Integer id);
}
