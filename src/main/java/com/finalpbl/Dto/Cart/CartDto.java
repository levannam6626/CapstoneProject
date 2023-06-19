package com.finalpbl.Dto.Cart;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    
    private List<CartItemDto> cartItems;
    
    private double totalCost;
}
