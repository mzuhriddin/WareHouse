package com.example.warehouse.entity;

import com.example.warehouse.entity.template.AbsNameEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true) //ota bola bir xil obp qolmasligi un
@AllArgsConstructor
@Data
@Entity
public class Measurement extends AbsNameEntity {
}
