package com.hometo.hometogetherapigateway.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token);
        } catch (NullPointerException e) {
            return false;
        } catch (ExpiredJwtException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Long getUserIdFromJwt(String jwt) {
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(jwt); // secretKey를 사용하여 복호화
        } catch (Exception e) {
        }
        Object userId = claims.getBody().get("userId");
        return Long.valueOf(userId.toString());
    }
}
