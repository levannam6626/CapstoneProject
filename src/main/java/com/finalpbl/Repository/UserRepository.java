package com.finalpbl.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finalpbl.Model.User;
public interface UserRepository extends JpaRepository<User, Long>{
    public Optional<User> findByEmail(String email);
}
