package com.example.warehouse.entity;

import com.example.warehouse.entity.template.AbsNameEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true) //ota bola bir xil obp qolmasligi un
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Category extends AbsNameEntity {
    @ManyToOne
    private Category category;
}