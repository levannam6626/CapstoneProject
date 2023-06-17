package com.finalpbl.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finalpbl.Model.Cart;
import com.finalpbl.Model.User;

public interface CartRepository extends JpaRepository<Cart, Long>{
    public List<Cart> findByUserOrderByCreatedDateDesc(User user);

    Cart deleteByUser(User user);
}
