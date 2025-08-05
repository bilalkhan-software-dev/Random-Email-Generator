package com.randomEmailGenerator.services.Impl;

import com.randomEmailGenerator.entity.User;
import com.randomEmailGenerator.exception.JwtTokenExpiredException;
import com.randomEmailGenerator.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.randomEmailGenerator.util.Constants.*;

@Slf4j
@Service
public class JwtServiceImpl implements JwtService {
    public String secretKey = SECRET_KEY;

    public JwtServiceImpl() {
        try {
            KeyGenerator instance = KeyGenerator.getInstance("HmacSHA256");
            SecretKey generateKey = instance.generateKey();
            secretKey = Base64.getEncoder().encodeToString(generateKey.getEncoded());
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
//        claims.put("sub", user.getUsername());
//        claims.put("iat", System.currentTimeMillis() / 1000);
//        claims.put("exp", System.currentTimeMillis() / 1000);
        claims.put("id", user.getId());
        claims.put("full name",user.getFullName());
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ JWT_EXPIRATION_TIME))
                .and()
                .signWith(getKey())
                .compact();
    }

    private Key getKey() {
        byte[] decode = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(decode);
    }
    private SecretKey decryptKey(String token) {
        byte[] decodedKey = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(decodedKey);
    }
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(decryptKey(token))
                    .build().parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new JwtTokenExpiredException("Token is expired");
        } catch (JwtException e) {
            throw new JwtTokenExpiredException("Jwt token is invalid");
        } catch (Exception e) {
            log.error("Error when extracting claims :{}", e.getMessage());
            throw e;
        }

    }

    @Override
    public String extractUsername(String token) {

        Claims extractAllClaims = extractAllClaims(token);
        return extractAllClaims.getSubject();
    }


    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        boolean isExpired = isTokenExpired(token);
        return username.equalsIgnoreCase(userDetails.getUsername()) && !isExpired;
    }

    private boolean isTokenExpired(String token) {
        Claims extractAllClaims = extractAllClaims(token);
        return extractAllClaims.getExpiration().before(new Date());
    }
}
