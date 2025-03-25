package com.scz.paymentservice;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @Column(name = "paymentid", nullable = false, length = 50)
    private String paymentid;

    @Column(name = "orderid", length = 50)
    private String orderid;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "status", length = 50)
    private String status;

}