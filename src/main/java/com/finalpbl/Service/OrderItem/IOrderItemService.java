package com.finalpbl.Service.OrderItem;

import java.util.List;
import com.finalpbl.Model.OrderItem;

public interface IOrderItemService {
    public List<OrderItem> getAllOrderItem();
    public OrderItem getOrderItemByID(long ID);
    // public List<OrderItem> getOrderItemsByOrderID(long OrderID);
    public String addOrderItem(OrderItem orderItem);
    // public String editOrder(OrderItem orderItem);
}
