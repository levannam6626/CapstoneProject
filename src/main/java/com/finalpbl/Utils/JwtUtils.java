package com.finalpbl.Utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.finalpbl.Config.UserDetailsImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
    // @Value("${application.security.jwt.secret-key}")
    private String secretKey = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    // @Value("${application.security.jwt.expiration}")
    private long jwtExpiration = 1000 * 60 * 60 * 24;
    // @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration = 604800000;
  
    public String extractUsername(String token) {
      return extractClaim(token, Claims::getSubject);
    }
  
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
      final Claims claims = extractAllClaims(token);
      return claimsResolver.apply(claims);
    }
  
    public String generateToken(UserDetailsImpl userDetails) {
      return generateToken(new HashMap<>(), userDetails);
    }
  
    public String generateToken(
        Map<String, Object> extraClaims,
        UserDetailsImpl userDetails
    ) {
      return buildToken(extraClaims, userDetails, jwtExpiration);
    }
  
    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetailsImpl userDetails,
            long expiration
    ) {
      return Jwts
              .builder()
              .setClaims(extraClaims)
              .setSubject(userDetails.getUsername())
              .setId(userDetails.getID())
              .setIssuedAt(new Date(System.currentTimeMillis()))
              .setExpiration(new Date(System.currentTimeMillis() + expiration))
              .signWith(getSignInKey(), SignatureAlgorithm.HS256)
              .compact();
    }
  
    public boolean isTokenValid(String token, UserDetailsImpl userDetails) {
      final String email = extractUsername(token);
      return (email.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
  
    private boolean isTokenExpired(String token) {
      return extractExpiration(token).before(new Date());
    }
  
    private Date extractExpiration(String token) {
      return extractClaim(token, Claims::getExpiration);
    }
  
    private Claims extractAllClaims(String token) {
      return Jwts
          .parserBuilder()
          .setSigningKey(getSignInKey())
          .build()
          .parseClaimsJws(token)
          .getBody();
    }
  
    private Key getSignInKey() {
      byte[] keyBytes = Decoders.BASE64.decode(secretKey);
      return Keys.hmacShaKeyFor(keyBytes);
    }
}
