package dev.konstantinou.urlshortener.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class JwtUtils {


    public static DecodedJWT validateSymmetricJwt(String providedJwt, String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT
                .require(algorithm)
                .build()
                .verify(providedJwt);
    }

    public static JwtDetails getValidatedJwtDetails(String providedJwt, String secret) {
        var decoded = validateSymmetricJwt(providedJwt, secret);
        var claims = decoded.getClaim("permissions");
        return new JwtDetails(decoded.getSubject(), decoded.getIssuer(), claims.asList(String.class));
    }

    public static String createDefaultSymmetricJwt(String username, List<String> permissions, String secret) {
        JwtOptions jwtOptions = new JwtOptions(
                UUID.randomUUID().toString(),
                "authenticator",
                Instant.now(),
                60,
                username,
                permissions
        );
        return createSymmetricJwt(jwtOptions, secret);
    }

    public static String createSymmetricJwt(JwtOptions jwtOptions, String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return  JWT
                .create()
                .withJWTId(jwtOptions.id())
                .withIssuer(jwtOptions.issuer())
                .withIssuedAt(jwtOptions.createdAt())
                .withExpiresAt(jwtOptions.createdAt().plus(jwtOptions.minutesUntilExpiration(), ChronoUnit.MINUTES))
                .withSubject(jwtOptions.subject())
                .withClaim("permissions", jwtOptions.permissions())
                .sign(algorithm);
    }


    public static String crateDefaultRsaJwt(String username, List<String> permissions, KeyPair keyPair) {
        JwtOptions jwtOptions = new JwtOptions(
                UUID.randomUUID().toString(),
                "authenticator",
                Instant.now(),
                60,
                username,
                permissions
        );
        return creteRsaJwt(jwtOptions ,keyPair);
    }

    public static String creteRsaJwt(JwtOptions jwtOptions, KeyPair keyPair) {
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        Algorithm algorithm = Algorithm.RSA256(rsaPublicKey, rsaPrivateKey);

        return JWT
                .create()
                .withJWTId(jwtOptions.id())
                .withIssuer(jwtOptions.issuer())
                .withIssuedAt(jwtOptions.createdAt())
                .withExpiresAt(jwtOptions.createdAt().plus(jwtOptions.minutesUntilExpiration(), ChronoUnit.MINUTES))
                .withSubject(jwtOptions.subject())
                .withClaim("permissions", jwtOptions.permissions())
                .sign(algorithm);
    }



}
