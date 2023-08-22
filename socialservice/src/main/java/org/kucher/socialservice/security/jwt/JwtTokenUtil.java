package org.kucher.socialservice.security.jwt;

import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;
import com.google.cloud.secretmanager.v1.SecretPayload;
import io.jsonwebtoken.*;
import org.kucher.socialservice.security.jwt.exception.ExpiredJwtTokenException;
import org.kucher.socialservice.security.jwt.exception.InvalidJwtTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil {

    private static String jwtIssuer;
    private static String secretVersion;
    private static SecretManagerServiceClient secretManagerServiceClient;

    @Value("${jwt.issuer}")
    public void setJwtIssuer(String jwtIssuer) {
        JwtTokenUtil.jwtIssuer = jwtIssuer;
    }
    @Value("${gcp.secret}")
    public void setSecretVersion(String secretVersion) {
        JwtTokenUtil.secretVersion = secretVersion;
    }
    @Autowired
    public void setSecretManagerServiceClient(SecretManagerServiceClient secretManagerServiceClient) {
        JwtTokenUtil.secretManagerServiceClient = secretManagerServiceClient;
    }

    private static String getSecret() {
        SecretPayload payload = secretManagerServiceClient.accessSecretVersion(secretVersion).getPayload();
        return payload.getData().toStringUtf8();
    }

    public static String generateAccessToken(Authentication auth) {
        String jwtSecret = getSecret();

        Date now = new Date();
        Date validity = new Date(now.getTime() + TimeUnit.DAYS.toMillis(1));

        Claims claims = Jwts.claims().setSubject(auth.getName());
        claims.put("authorities", auth.getAuthorities());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(jwtIssuer)
                .setIssuedAt(now)
                .setExpiration(validity) // 1 week
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public static Authentication getAuthentication(String token) {
        String jwtSecret = getSecret();

        Claims claims = validate(token, jwtSecret);

        String username = claims.getSubject();

        //Из JWT из поля authority, достается ArrayList<LinkedHashMap<String, String>>
        //Его приеобразовываем в список List<GrantedAuthority>
        List<GrantedAuthority> authorities = ((List<?>) claims.get("authorities"))
                .stream()
                .map(authority -> new SimpleGrantedAuthority(((HashMap<String, String>) authority).get("authority")))
                .collect(Collectors.toList());

        UserDetails userDetails = new User(username, "password", authorities);

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    private static Claims validate(String token, String secret) {

        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        }
        catch (SignatureException | IllegalArgumentException | UnsupportedJwtException | MalformedJwtException ex) {
            throw new InvalidJwtTokenException("Invalid token");
        }
        catch (ExpiredJwtException ex) {
            throw new ExpiredJwtTokenException("Token Expired");
        }
    }
}