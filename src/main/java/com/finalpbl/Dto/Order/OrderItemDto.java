package com.finalpbl.Dto.Order;

import com.finalpbl.Dto.Product.ProductDto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    
    @NotNull
    private double price;
    
    @NotNull
    private  int quantity;
    
    @NotNull
    private  long orderId;
    
    @NotNull
    private  ProductDto product;
}
