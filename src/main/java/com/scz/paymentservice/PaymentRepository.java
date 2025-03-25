package com.scz.paymentservice;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, String> {

      Optional<Payment> findByOrderid(String orderid);
}