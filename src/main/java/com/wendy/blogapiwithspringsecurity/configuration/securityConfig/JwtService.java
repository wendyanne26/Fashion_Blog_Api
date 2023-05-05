package com.wendy.blogapiwithspringsecurity.configuration.securityConfig;

import com.wendy.blogapiwithspringsecurity.dtos.UserDto;
import com.wendy.blogapiwithspringsecurity.models.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${application.secretKey}")
     String SECRET_KEY ;
    @Value("${application.expiration}")
    Long expiration;

    public String extractUsername(String jwtToken) {
        return extractClaims(jwtToken, Claims::getSubject);
    }

    private <T> T extractClaims(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String jwtToken){
        return  Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();

    }

    private Key getSignInKey() {
       byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);
       return Keys.hmacShaKeyFor(keyByte);
    }

    public boolean isTokenValidated(String jwtToken, UserDetails userDetails) {
        String username = extractUsername(jwtToken);
        return (userDetails.getUsername().equals(username) && !isExpired(jwtToken));
    }

    private boolean isExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(String jwtToken) {
        return extractClaims(jwtToken, Claims::getExpiration);
    }
    public String generateToken(UserDetails userDetails){
        //collections to get simple authorities
        Collection<? extends SimpleGrantedAuthority> authorities =
                (Collection<? extends SimpleGrantedAuthority>) userDetails.getAuthorities();

        //initialize an arraylist to hold the authorities
        List<String> authorityName = new ArrayList<>();
        //loop through and add the simple authorities to the list
        for(SimpleGrantedAuthority authority: authorities){
            authorityName.add(authority.getAuthority());
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", authorityName);
        return buildToken(claims, userDetails, expiration);
    }

    private String buildToken(Map<String, Object> claims, UserDetails userDetails, Long expiration) {
        return  Jwts
                .builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .setSubject(userDetails.getUsername())
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date (System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

    }
}
