package com.finalpbl.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finalpbl.Model.OrderItem;
import com.finalpbl.Model.ProductOrder;
import com.finalpbl.Model.Products;


public interface OrderItemRepository extends JpaRepository<OrderItem,Long>{

    public OrderItem findByProducts(Products products);

    public OrderItem findOrderItemByProductsAndProductOrder(Products products, ProductOrder newproductOrder);
}
