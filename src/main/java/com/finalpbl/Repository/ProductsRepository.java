package com.finalpbl.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.finalpbl.Model.Products;

public interface ProductsRepository extends JpaRepository<Products,Long>{
    public Optional<Products> findByProductName(String product_name);
    public Products findProductsByProductId(Long id);
    public List<Products> findAllByCategoryId(Long id);

    @Query(value = "SELECT * FROM products WHERE  (:product_name is null or product_name like %:product_name%)", nativeQuery = true)
    List<Products> getProductsSearch(@Param("product_name") String product_name);

    @Query(value = "SELECT * FROM products WHERE category_id = :category_id", nativeQuery = true)
    List<Products> getProductsByCategoryId(@Param("category_id") Long category_id);
}
