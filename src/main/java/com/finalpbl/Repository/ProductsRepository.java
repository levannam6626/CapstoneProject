package com.finalpbl.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finalpbl.Model.Products;

public interface ProductsRepository extends JpaRepository<Products,Long>{
    public Optional<Products> findByProductName(String product_name);
}
