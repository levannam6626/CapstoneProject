package com.finalpbl.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finalpbl.Model.Payment;
public interface PaymentRepository extends JpaRepository<Payment,Long>{
    
}
