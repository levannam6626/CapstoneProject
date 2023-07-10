package com.finalpbl.Service.ProductOrder;

import java.util.List;

import com.finalpbl.Dto.Order.OrderUpdateRequestDto;
import com.finalpbl.Dto.Order.ProductOrderDto;
import com.finalpbl.Model.ProductOrder;

public interface IProductOrderService {
    public List<ProductOrderDto> getAllOrders();
    public List<ProductOrderDto> getOrdersByUser(String email);
    public ProductOrder getOrderByID(long OrderID);
<<<<<<< HEAD
    public String PlaceOrder(String email);
=======
    public String PlaceOrder(ProductOrderDto productOrderDto, String email);
>>>>>>> 05261b32106550a7c31b87a14f2ca99c338a7220
    public String editOrder(OrderUpdateRequestDto order);
}
