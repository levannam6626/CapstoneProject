package com.finalpbl.Service.ProductOrder;

import java.util.List;

import com.finalpbl.Dto.Order.ProductOrderDto;
import com.finalpbl.Model.ProductOrder;

public interface IProductOrderService {
    public List<ProductOrderDto> getAllOrders();
    public List<ProductOrderDto> getOrdersByUser(String email);
    public ProductOrder getOrderByID(long OrderID);
    public String PlaceOrder(String email);
    public String editOrder(ProductOrderDto order);
}
