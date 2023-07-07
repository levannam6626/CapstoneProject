package com.finalpbl.Service.Cart;

import java.util.List;

import com.finalpbl.Dto.Cart.AddEditCartDto;
import com.finalpbl.Dto.Cart.CartDto;

public interface ICartService {
    public CartDto findByUserOrderByCreatedDateDesc(String email);
    public String AddCartItem(AddEditCartDto cartItemDto, String email);
    public String UpdateCart(AddEditCartDto cartItemDto);
    public String UpdateCartMultiple(List<AddEditCartDto> cartItemDtos);
    public String DeleteCart(long CartId);
    public String DeleteCartByUser(String email);
    public String DeleteOrderdItem(String email);
}
