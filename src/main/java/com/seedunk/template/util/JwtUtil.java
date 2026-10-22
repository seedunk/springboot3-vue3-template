package com.seedunk.template.util;
 
import java.security.Key;
import java.util.Date; 

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
     
    // @Value("${app.jwtSecret}") private String SECRET_KEY;
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    //@Value("${app.jwtExpirationMs}")  private int EXPIRATION_TIME;
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours

     
    public String generateJwtToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            System.getLogger(JwtUtil.class.getName()).log(System.Logger.Level.ERROR, "Invalid JWT signature: {}", e );
       
        } catch (MalformedJwtException e) { 
            System.getLogger(JwtUtil.class.getName()).log(System.Logger.Level.ERROR, "Invalid JWT token: {}", e ); 
        } catch (ExpiredJwtException e) {
            
            System.getLogger(JwtUtil.class.getName()).log(System.Logger.Level.ERROR, "JWT token is expired: {}", e );
    
        } catch (UnsupportedJwtException e) {
            
            System.getLogger(JwtUtil.class.getName()).log(System.Logger.Level.ERROR, "JWT token is unsupported: {}", e );
       
        } catch (IllegalArgumentException e) {
            
            System.getLogger(JwtUtil.class.getName()).log(System.Logger.Level.ERROR, "JWT claims string is empty: {}", e );
           
        }
        return false;
    }

 
}
