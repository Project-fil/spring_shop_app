package com.github.ratel.security;

import com.github.ratel.exceptions.InvalidTokenException;
import io.jsonwebtoken.*;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String secretWord;

    public String generateToken(UserDetailsImpl payload) {
        Date date = Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", payload.getId());
        claims.put("email", payload.getUsername());
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, secretWord)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretWord).parseClaimsJws(token);
            return true;
        } catch (InvalidTokenException |
                SignatureException |
                MalformedJwtException |
                ExpiredJwtException |
                IllegalArgumentException ex) {
            log.error(ex.getMessage());
            return false;
        }
    }

    public String getIdFromToken(String token) {
        if(token.isEmpty()) {
            return "";
        }
        Claims claims = Jwts.parser()
                .setSigningKey(secretWord)
                .parseClaimsJws(token)
                .getBody();
        return String.valueOf(claims.get("id"));
    }

    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretWord)
                .parseClaimsJws(token)
                .getBody();
        return String.valueOf(claims.get("email"));
    }
}
