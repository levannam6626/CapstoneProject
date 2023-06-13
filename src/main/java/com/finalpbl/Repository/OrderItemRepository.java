package com.finalpbl.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finalpbl.Model.OrderItem;
import com.finalpbl.Model.ProductOrder;


public interface OrderItemRepository extends JpaRepository<OrderItem,Long>{
}
