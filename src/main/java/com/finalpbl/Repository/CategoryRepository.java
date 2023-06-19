package com.finalpbl.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finalpbl.Model.Category;


public interface CategoryRepository extends JpaRepository<Category,Long>{
    public Optional<Category> findByCategoryName(String category_name);
}
