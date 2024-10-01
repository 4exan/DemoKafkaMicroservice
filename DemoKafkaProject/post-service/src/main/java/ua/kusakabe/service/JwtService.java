package ua.kusakabe.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.function.Function;

@Service
public class JwtService {

    private final SecretKey secretKey;
    private static final long EXPIRATION_TIME = 1800000; //30 minutes

    public JwtService() {
        String secretString = "18912319123812391812417912351791192543124159";
        byte[] keyBytes = Base64.getDecoder().decode(secretString.getBytes(StandardCharsets.UTF_8));
        this.secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(    //Take username from token
                Jwts.parser()
                        .verifyWith(secretKey)  //Decrypt with secretKey
                        .build()
                        .parseSignedClaims(token)
                        .getPayload()); //Take payload
    }

}
