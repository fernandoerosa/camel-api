package br.com.wmw.auth.helper;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import io.smallrye.jwt.build.Jwt;

public class JwtService {

    public static String generateToken(String email) {
        List<String> roles = new ArrayList<>();
        roles.add("USER");

        String token = Jwt.issuer("http://example.com/issuer")
                .upn(email)
                .expiresIn(Duration.ofHours(1))
                .claim("coreUrl", "localhost:8082")
                .claim("roles", roles)
                .sign();

        return token;
    }

    public static boolean authenticate(String email, String password) {
        return true;
    }
}
