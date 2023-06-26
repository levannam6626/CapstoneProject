package com.finalpbl.Dto.Order;

import java.time.LocalDateTime;
import java.util.List;

import com.finalpbl.Dto.User.UserDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderDto {
    private long ID;

    private Double totalPrice;

    private LocalDateTime createdDate;

    private UserDto user;

    private List<OrderItemDto> order_items;
}
    