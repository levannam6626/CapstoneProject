package com.finalpbl.Mapper;

import java.util.function.Function;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalpbl.Dto.Order.ProductOrderDto;
import com.finalpbl.Dto.User.UserDto;
import com.finalpbl.Model.ProductOrder;

@Service
public class OrderResponseMapper implements Function<ProductOrder, ProductOrderDto> {

    @Autowired
    private OrderItemResponseMapper orderItemResponseMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductOrderDto apply(ProductOrder order) {
        return new ProductOrderDto(order.getId(), order.getFullName(), order.getDeliveryAddress(), order.getPhone(), order.getAdditionalNotes(), order.getTotalPrice(),order.getOrderStatus().name(), order.getCreatedDate(), modelMapper.map(order.getUser(), UserDto.class), order.getOrderItem().stream().map(orderItemResponseMapper).collect(Collectors.toList()));
    }
    
}
