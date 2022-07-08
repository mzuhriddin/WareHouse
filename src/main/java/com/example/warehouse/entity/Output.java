package com.example.warehouse.entity;

import com.example.warehouse.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true) //ota bola bir xil obp qolmasligi un
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Output extends AbsEntity {
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp date;

    @ManyToOne
    private Warehouse warehouse;

    @ManyToOne
    private Currency currency;

    private String factureNumber;

    @ManyToOne
    private Client client;
}
