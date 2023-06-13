package com.finalpbl.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finalpbl.Model.ProductOrder;
import com.finalpbl.Model.User;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long>{
    public Optional<ProductOrder> findByUser(User user);
}
