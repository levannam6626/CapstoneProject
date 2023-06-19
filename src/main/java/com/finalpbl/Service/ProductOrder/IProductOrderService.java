package com.finalpbl.Service.ProductOrder;

import java.util.List;

import com.finalpbl.Model.ProductOrder;

public interface IProductOrderService {
    public List<ProductOrder> getAllOrders();
    public List<ProductOrder> getOrdersByUser(String email);
    public ProductOrder getOrderByID(long OrderID);
    public String PlaceOrder(String email);
}
