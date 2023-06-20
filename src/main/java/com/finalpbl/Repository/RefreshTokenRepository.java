package com.finalpbl.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.finalpbl.Model.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long>{
    Optional<RefreshToken> findByToken(String token);
}
