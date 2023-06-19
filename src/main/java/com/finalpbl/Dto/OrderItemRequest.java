package com.finalpbl.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequest {

    private long id;

    private int quantity;

    private ProductDto product;

    private UserDto user;
}
