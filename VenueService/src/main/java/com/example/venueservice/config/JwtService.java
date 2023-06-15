package com.example.venueservice.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class JwtService {
    private static final String SECRET_KEY="34743777217A25432A462D4A614E645267556B586E3272357538782F413F4428";

    public String extractUsername(final String token){
        Claims claims= Jwts.parserBuilder().setSigningKey(getSigninInKey()).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }


    private Key getSigninInKey(){
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
