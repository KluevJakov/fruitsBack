package ru.jafix.fruties.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

    @Value("${secret}")
    protected String secret;

    @Value("${exp_time}")
    protected String expirationTime;

    public String generate(String subject) {
        Date date = new Date();
        return Jwts.builder()
                .signWith(getSecretKey(), Jwts.SIG.HS512)
                .subject(subject)
                .issuedAt(date)
                .expiration(new Date(date.getTime() + Long.parseLong(expirationTime)))
                .compact();
    }

    public String getUsernameFromToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token);

            return (String) claims.getPayload().get("sub");
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }

    public boolean validate(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    private SecretKey getSecretKey() {
        byte[] encodeKey = Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(encodeKey);
    }
}
