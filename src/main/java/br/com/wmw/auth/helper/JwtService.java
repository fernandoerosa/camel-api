package br.com.wmw.auth.helper;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

import io.smallrye.jwt.build.Jwt;

public class JwtService {

    public static String generateToken(String email) {
        Set<String> roles = new HashSet<>();
        roles.add("User");

        String token = Jwt.issuer("http://example.com/issuer")
                .upn(email)
                .groups(roles)
                .expiresIn(Duration.ofHours(1))
                .sign();

        return token;
    }

    public static boolean authenticate(String email, String password) {
        return true;
    }
}
