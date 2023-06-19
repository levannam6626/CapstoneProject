package com.finalpbl.Service.ProductOrder;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalpbl.Dto.OrderItemRequest;
import com.finalpbl.Dto.ProductOrderDto;
import com.finalpbl.Model.OrderItem;
import com.finalpbl.Model.ProductOrder;
import com.finalpbl.Model.Products;
import com.finalpbl.Model.User;
import com.finalpbl.Repository.OrderItemRepository;
import com.finalpbl.Repository.ProductOrderRepository;
import com.finalpbl.Repository.ProductsRepository;
import com.finalpbl.Repository.UserRepository;

@Service
public class ProductOrderService implements IProductOrderService{

    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired 
    private UserRepository userRepository;

    @Override
    public List<ProductOrder> getAllOrders() {
        List<ProductOrder> orders = productOrderRepository.findAll();
        return orders;
    }

    @Override
    public ProductOrder getOrderByID(long OrderID) {
        ProductOrder order = productOrderRepository.findById(OrderID).orElseThrow();
        return order;
    }

    @Override
    public String addToCart(OrderItemRequest orderItemRequest) {
        User user = userRepository.findByEmail(orderItemRequest.getUser().getEmail()).orElse(null);
        if(user != null) {
            ProductOrder productOrder = productOrderRepository.findByUser(user).filter(productOrd -> !productOrd.getPaidStatus()).orElse(null);
            if(productOrder == null) {
                productOrder = new ProductOrder();
                productOrder.setCreatedDate(LocalDateTime.now());
                productOrder.setTotalPrice(0.0);
                productOrder.setPaidStatus(false);
                productOrder.setUser(user);
                productOrderRepository.save(productOrder);
            }
            ProductOrder newproductOrder = productOrderRepository.findByUser(user).filter(productOrd -> !productOrd.getPaidStatus()).orElse(null);
            Products products = productsRepository.findByProductName(orderItemRequest.getProduct().getProductName()).orElseThrow(null);
            OrderItem orderItem = orderItemRepository.findOrderItemByProductsAndProductOrder(products, newproductOrder);
            if( orderItem == null) {
                orderItem = new OrderItem();
                orderItem.setProductOrder(newproductOrder);
                orderItem.setProducts(products);
                orderItem.setQuantity(orderItemRequest.getQuantity());
                orderItemRepository.save(orderItem);
            }else {
                orderItem.setQuantity(orderItem.getQuantity() + orderItemRequest.getQuantity());
                orderItemRepository.save(orderItem);
            }
        }
        return "Add To Cart Success";
    }

    @Override
    public String addOrder(ProductOrderDto order, String email) {
        ProductOrder order2 = new ProductOrder();
        User user = userRepository.findByEmail(email).orElseThrow();
        order2.setUser(user);
        order2.setTotalPrice(order.getTotalPrice());
        order2.setCreatedDate(order.getCreatedDate());
        productOrderRepository.save(order2);
        return "Add Order Success";
    }

    @Override
    public String editOrder(ProductOrder order) {
        ProductOrder orderValidate = productOrderRepository.findById(order.getId()).orElseThrow();
        if(orderValidate != null)
        {
            ProductOrder order2 = new ProductOrder();
            order2.setUser(order.getUser());
            order2.setOrderItem(order.getOrderItem());
            productOrderRepository.save(order2);
            return "Edit order success";
        }
        return "Order not exist";
    }
    
}
