package org.kucher.userservice.security.jwt;

import org.kucher.userservice.security.jwt.exception.ExpiredJwtTokenException;
import org.kucher.userservice.security.jwt.exception.InvalidJwtTokenException;
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;
import com.google.cloud.secretmanager.v1.SecretPayload;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Utility class for handling JWT (JSON Web Token) operations.
 */
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

    /**
     * Generates an access token (JWT) based on the provided authentication details.
     *
     * @param auth The authentication object containing user details.
     * @return The generated access token.
     */
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

    /**
     * Retrieves an Authentication object from the provided JWT token.
     *
     * @param token The JWT token from which to retrieve authentication details.
     * @return An Authentication object containing user details.
     * @throws InvalidJwtTokenException if the token is invalid.
     * @throws ExpiredJwtTokenException if the token has expired.
     */
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