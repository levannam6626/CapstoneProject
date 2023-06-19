package com.finalpbl.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finalpbl.Model.ProductOrder;
import com.finalpbl.Model.User;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long>{
    public List<ProductOrder> findByUserOrderByCreatedDateDesc(User user);
}
