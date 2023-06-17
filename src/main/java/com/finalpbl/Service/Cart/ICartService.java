package com.finalpbl.Service.Cart;

import java.util.List;

import com.finalpbl.Model.Cart;
import com.finalpbl.Model.User;

public interface ICartService {
    public List<Cart> findByUserOrderByCreatedDateDesc(User user);
    public String AddCartItem();
}
