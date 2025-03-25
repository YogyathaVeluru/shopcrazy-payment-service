package com.scz.paymentservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("shopcrazy/payment/v1")
public class PaymentController {

    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    AuthService authService;

    @Autowired
    PaymentRepository paymentRepository;

    @GetMapping("get/payment/{orderid}")
    public ResponseEntity<?> getPayment(@PathVariable("orderid") String orderid)
    {
        Payment payment=null;

        if(paymentRepository.findByOrderid(orderid).isPresent())
        {
            payment =  paymentRepository.findByOrderid(orderid).get();
            log.info("Payment found: {}", payment);
            return ResponseEntity.ok(payment);
        }
        else
        {
            log.info("Payment not found for orderid: {}", orderid);
            return ResponseEntity.ok().body(null);
        }

    }

    @PostMapping("create")
    public ResponseEntity<?> createPayment(@RequestBody PaymentRequest paymentRequest,
                                           @RequestHeader("Authorization") String token) throws InterruptedException {

        log.info("Received request to create payment: {}", paymentRequest);
        ValidateResponse validateResponse = authService.validateToken(token);
        if(validateResponse.isValid())
        {
            log.info("Token is valid: {}", token);
            log.info("Received request to create payment: {}", paymentRequest);

            Thread.sleep(20000);

//            throw new RuntimeException("Chaos Testing in Progress");

            Payment payment = new Payment();
            payment.setPaymentid(String.valueOf(new Random().nextInt()));
            payment.setOrderid(paymentRequest.getOrder_id());
            payment.setAmount(paymentRequest.getAmount());
            payment.setStatus("PENDING");
            paymentRepository.save(payment);

            log.info("Payment created successfully: {}", payment);

            return ResponseEntity.ok(payment.getPaymentid());
        }
        else
        {
            log.info("Token is invalid: {}", token);
            return ResponseEntity.badRequest().body("Invalid token");
        }
    }


}
