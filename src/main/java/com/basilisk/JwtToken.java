package com.basilisk;

import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtToken {

    private final String SECRET_KEY = "liberate-tutume-ex-inferis-ad-astra-per-aspera";
    private final String AUDIENCE = "BasiliskWebUI";

    private Claims getClaims(String token){ //validator
//        Meng-convert secret jadi JWTParser dan mem-verified String token
        JwtParser parser = Jwts.parser().setSigningKey(SECRET_KEY);

//        mem-verified String token, sekaligus meng-ekstrak signature dan claims
        Jws<Claims> signatureAndClaims = parser.parseClaimsJws(token);

//        Ambil calims/payloadnya saja
        Claims claims = signatureAndClaims.getBody();
        return claims;
    }

//  Method yang digunakan untuk membuat token dari hasil yang diterima oleh request 3rd party application dan user
    public String generateToken(String subject, String username, String secretKey, String role, String audience){
        JwtBuilder builder = Jwts.builder();
        builder = builder.setSubject(subject)
                .claim("username", username)
                .claim("role", role)
                .setIssuer("http://localhost:8070")
                .setAudience(audience)
                .signWith(SignatureAlgorithm.HS256, secretKey);
        return builder.compact();
    }

    public String getUsername(String token){
        try {
            Claims claims = getClaims(token);
            return claims.get("username", String.class);
        }catch (Exception exception){
            return null;
        }
    }

//    Memvalidasi apakah token ini benar atau tidak
    public Boolean validateToken(String token, UserDetails userDetails){
        Claims claims = getClaims(token);
//        Mendapatkan username
        String user = getUsername(token);
//        Mendapatkan Audience
        String audience = claims.getAudience();
//        Jika  username dan audience matched, return true
        return (user.equals(userDetails.getUsername()) && audience.equals(AUDIENCE));
    }
}
