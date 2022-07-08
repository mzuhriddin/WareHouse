package com.example.warehouse.entity;

import com.example.warehouse.entity.template.AbsNameEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@Data
public class Warehouse extends AbsNameEntity {
}
