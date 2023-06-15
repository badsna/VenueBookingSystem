package com.example.apigateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.function.Function;

@Service
public class JwtUtil {
    private static final String SECRET_KEY = "34743777217A25432A462D4A614E645267556B586E3272357538782F413F4428";

    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSigninInKey()).build().parseClaimsJws(token);
    }

    public String extractRole(String token) {
        return extractClam(token, Claims::getIssuer);
    }


    public <T> T extractClam(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigninInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigninInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
