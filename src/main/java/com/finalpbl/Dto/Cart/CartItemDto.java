package com.finalpbl.Dto.Cart;

import com.finalpbl.Dto.Product.ProductDto;
import com.finalpbl.Model.Products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private long id;

    private int quantity;

    private ProductDto products;
}
