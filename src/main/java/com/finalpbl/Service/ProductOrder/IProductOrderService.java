package com.finalpbl.Service.ProductOrder;

import java.util.List;

import com.finalpbl.Dto.Order.ProductOrderDto;
import com.finalpbl.Model.ProductOrder;

public interface IProductOrderService {
    public List<ProductOrder> getAllOrders();
    public ProductOrder getOrderByID(long OrderID);
    public String addOrder(ProductOrderDto order, String email);
    public String editOrder(ProductOrder order);
}
