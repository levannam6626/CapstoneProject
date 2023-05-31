package com.finalpbl.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.finalpbl.Model.User;
public interface UserRepository extends JpaRepository<User, Long>{
    public Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE  (:email is null or email like %:email%) and is_deleted = 0 LIMIT :limitNumber", nativeQuery = true)
    List<User> findUserSearch(@Param("email") String email, @Param("limitNumber") int limitNumber);
}
