package com.finalpbl.Service.ProductOrder;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.finalpbl.Constant.OrderStatus;
import com.finalpbl.Dto.Cart.CartDto;
import com.finalpbl.Dto.Cart.CartItemDto;
import com.finalpbl.Dto.Order.OrderUpdateRequestDto;
import com.finalpbl.Dto.Order.ProductOrderDto;
import com.finalpbl.Mapper.OrderResponseMapper;
import com.finalpbl.Model.OrderItem;
import com.finalpbl.Model.ProductOrder;
import com.finalpbl.Model.Products;
import com.finalpbl.Model.User;
import com.finalpbl.Repository.OrderItemRepository;
import com.finalpbl.Repository.ProductOrderRepository;
import com.finalpbl.Repository.ProductsRepository;
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

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired OrderResponseMapper orderResponseMapper;
    @Override
    public List<ProductOrderDto> getAllOrders() {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        List<ProductOrder> orders = productOrderRepository.findAll(sort);
        List<ProductOrderDto> orderDtos = orders.stream().map(orderResponseMapper).collect(Collectors.toList());
        return orderDtos;
    }

    @Override
    public List<ProductOrderDto> getOrdersByUser(String email)
    {
        User user = userRepository.findByEmail(email).orElseThrow();
        List<ProductOrder> orders = productOrderRepository.findByUserOrderByCreatedDateDesc(user);
        List<ProductOrderDto> orderDtos = orders.stream().map(orderResponseMapper).collect(Collectors.toList());
        return orderDtos;
    }

    @Override
    public ProductOrder getOrderByID(long OrderID) {
        ProductOrder order = productOrderRepository.findById(OrderID).orElseThrow();
        return order;
    }

    @Override
    public String PlaceOrder(ProductOrderDto productOrderDto, String email) {
        CartDto cartDto = cartService.findByUserOrderByCreatedDateDesc(email);
        List<CartItemDto> cartItemDtos = cartDto.getCartItems();
        ProductOrder order = new ProductOrder();
        order.setFullName(productOrderDto.getFullName());
        order.setDeliveryAddress(productOrderDto.getDeliveryAddress());
        order.setPhone(productOrderDto.getPhone());
        order.setAdditionalNotes(productOrderDto.getAdditionalNotes());
        order.setCreatedDate(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
        order.setUser(userRepository.findByEmail(email).orElseThrow());
        order.setTotalPrice(cartDto.getTotalCost());
        order.setOrderStatus(OrderStatus.PENDING);
        productOrderRepository.save(order);

        for(CartItemDto items: cartItemDtos)
        {
            if(items.isSelected() == true)
            {
                OrderItem orderItem = new OrderItem();
                orderItem.setPrice(items.getProducts().getProductPrice() * items.getQuantity());
                Products products = items.getProducts();
                orderItem.setProductorder(order);
                orderItem.setProducts(items.getProducts());
                orderItem.setQuantity(items.getQuantity());
                products.setProductQuantity(products.getProductQuantity() - items.getQuantity());
                orderItemRepository.save(orderItem);
            }
        }
        cartService.DeleteOrderdItem(email);
        return "Add Order Success";
    }

    @Override
    public String editOrder(OrderUpdateRequestDto order) {
        ProductOrder orderValidate = productOrderRepository.findById(order.getID()).orElseThrow();
        if(orderValidate != null)
        {
            orderValidate.setOrderStatus(OrderStatus.valueOf(order.getStatus()));
            productOrderRepository.save(orderValidate);
            return "Edit order success";
        }
        return "Order not exist";
    }
    
}
