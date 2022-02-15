package ru.gretchen.eventorganizer.service.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import ru.gretchen.eventorganizer.model.entity.User;
import ru.gretchen.eventorganizer.service.TokenService;

import java.util.Date;
import java.util.HashMap;

@Service
public class TokenServiceImpl implements TokenService {

    @Override
    public String generateToken(User user) {
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 100000000L))
                .signWith(SignatureAlgorithm.ES256, "secretKey")
                .compact();
    }
}
