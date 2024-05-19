package uz.yusa.avtokredits.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uz.yusa.avtokredits.domain.User;
import uz.yusa.avtokredits.domain.VerificationToken;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.StringJoiner;

@Component
public class JwtProvider {
    @Value("${secret.key}")
    private String secretKey;

    public String generate(User user) {
        StringJoiner roles = new StringJoiner(",");

        // Check if user roles are null
        if (user.getRoles() == null) {
            throw new IllegalStateException("User roles are not set.");
        }

        user.getRoles().forEach(role -> roles.add(role.getName().toUpperCase()));

        return Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 2400 * 10000))
                .claim("roles", roles.toString())
                .signWith(key())
                .compact();
    }
    public String generateVerificationToken(String email) {



        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 2400 * 10000))
//                .claim("roles", roles.toString())
                .signWith(key())
                .compact();
    }

    private SecretKey key() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public Claims parse(String token){
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validate(final String token) {
        try {
            Claims claims = parse(token);
            if (claims.getExpiration().after(new Date())) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}





