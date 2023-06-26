package com.finalpbl.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.finalpbl.Model.Cart;
import com.finalpbl.Model.User;

public interface CartRepository extends JpaRepository<Cart, Long>{
    public List<Cart> findByUserOrderByCreatedDateDesc(User user);

    public List<Cart> deleteByUser(User user);

    @Query(value = "select * from cart where product_id = :product_id and user_id = :user_id", nativeQuery = true)
    public Optional<Cart> findByProductsandUser(@Param("product_id")Long product_id,@Param("user_id") Long user_id);

    @Modifying
    @Query(value = "delete from cart where user_id = :user_id and selected = 1", nativeQuery = true)
    public void deleteBySelectedAndUser(@Param("user_id") Long user_id);
}
