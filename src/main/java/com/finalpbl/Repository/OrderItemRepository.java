package com.finalpbl.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finalpbl.Model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long>{

}
