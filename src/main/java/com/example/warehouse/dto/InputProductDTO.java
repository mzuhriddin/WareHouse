package com.example.warehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InputProductDTO {
    private Integer productId;
    private Integer amount;
    private double price;
    private Date expireDate;
}
