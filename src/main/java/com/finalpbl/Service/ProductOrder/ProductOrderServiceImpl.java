package com.finalpbl.Service.ProductOrder;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalpbl.Dto.Cart.CartDto;
import com.finalpbl.Dto.Cart.CartItemDto;
import com.finalpbl.Model.OrderItem;
import com.finalpbl.Model.ProductOrder;
import com.finalpbl.Model.User;
import com.finalpbl.Repository.OrderItemRepository;
import com.finalpbl.Repository.ProductOrderRepository;
import com.finalpbl.Repository.UserRepository;
import com.finalpbl.Service.Cart.ICartService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductOrderServiceImpl implements IProductOrderService{

    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Autowired 
    private UserRepository userRepository;

    @Autowired
    private ICartService cartService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public List<ProductOrder> getAllOrders() {
        List<ProductOrder> orders = productOrderRepository.findAll();
        return orders;
    }

    @Override
    public List<ProductOrder> getOrdersByUser(String email)
    {
        User user = userRepository.findByEmail(email).orElseThrow();
        List<ProductOrder> orders = productOrderRepository.findByUserOrderByCreatedDateDesc(user);
        return orders;
    }

    @Override
    public ProductOrder getOrderByID(long OrderID) {
        ProductOrder order = productOrderRepository.findById(OrderID).orElseThrow();
        return order;
    }

    @Override
    public String PlaceOrder(String email) {
        CartDto cartDto = cartService.findByUserOrderByCreatedDateDesc(email);

        List<CartItemDto> cartItemDtos = cartDto.getCartItems();

        ProductOrder order = new ProductOrder();
        order.setCreatedDate(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
        order.setUser(userRepository.findByEmail(email).orElseThrow());
        order.setTotalPrice(cartDto.getTotalCost());
        productOrderRepository.save(order);

        for(CartItemDto items: cartItemDtos)
        {
            if(items.isSelected() == true)
            {
                OrderItem orderItem = new OrderItem();
                orderItem.setPrice(items.getProducts().getProductPrice());
                orderItem.setProductorder(order);
                orderItem.setProducts(items.getProducts());
                orderItem.setQuantity(items.getQuantity());
                orderItemRepository.save(orderItem);
            }
        }
        cartService.DeleteOrderdItem(email);
        return "Add Order Success";
    }

    // @Override
    // public String editOrder(ProductOrder order) {
    //     ProductOrder orderValidate = productOrderRepository.findById(order.getId()).orElseThrow();
    //     if(orderValidate != null)
    //     {
    //         ProductOrder order2 = new ProductOrder();
    //         order2.setUser(order.getUser());
    //         order2.setOrderItem(order.getOrderItem());
    //         productOrderRepository.save(order2);
    //         return "Edit order success";
    //     }
    //     return "Order not exist";
    // }
    
}
