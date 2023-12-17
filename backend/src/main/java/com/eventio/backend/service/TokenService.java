package com.eventio.backend.service;

import com.eventio.backend.dto.KorisnikDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Value("${jwt.secret-key}")
    private String secretKey;

    public boolean isTokenValid(String jwtToken, KorisnikDTO currentUser) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody();

            String username = claims.getSubject();
            String uloga = claims.get("uloga", String.class);

            if (currentUser != null && username.equals(currentUser.getUsername()) && uloga.equals(currentUser.getUloga().name())) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}