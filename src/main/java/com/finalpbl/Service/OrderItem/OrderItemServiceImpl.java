package com.finalpbl.Service.OrderItem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalpbl.Model.OrderItem;
import com.finalpbl.Repository.OrderItemRepository;
import com.finalpbl.Repository.ProductOrderRepository;
@Service
public class OrderItemServiceImpl implements IOrderItemService{

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductOrderRepository orderRepository;

    @Override
    public List<OrderItem> getAllOrderItem() {
        List<OrderItem> orderItems = orderItemRepository.findAll();
        return orderItems;
    }

    @Override
    public OrderItem getOrderItemByID(long ID) {
        OrderItem orderItem = orderItemRepository.findById(ID).orElseThrow();
        return orderItem;
    }

    
    // @Override
    // public List<OrderItem> getOrderItemsByOrderID(long OrderID) {
    //     Order order = orderRepository.findById(OrderID).orElseThrow();
    //     List<OrderItem> orderItems = orderItemRepository.findByOrder(order).orElseThrow();
    // }


    @Override
    public String addOrderItem(OrderItem orderItem) {

       orderItemRepository.save(orderItem);
       return "Add orderItem Success";
    }

    // @Override
    // public String editOrder(OrderItem orderItem) {
       
    // }

    
}
