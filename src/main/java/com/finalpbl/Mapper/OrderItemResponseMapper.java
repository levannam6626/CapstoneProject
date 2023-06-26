package com.finalpbl.Mapper;

import java.util.function.Function;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalpbl.Dto.Order.OrderItemDto;
import com.finalpbl.Dto.Product.ProductDto;
import com.finalpbl.Model.OrderItem;

@Service
public class OrderItemResponseMapper implements Function<OrderItem, OrderItemDto>{

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OrderItemDto apply(OrderItem item) {
       return new OrderItemDto(item.getPrice(), item.getQuantity(), item.getProductorder().getId(), modelMapper.map(item.getProducts(), ProductDto.class));
    }
    
    
}
