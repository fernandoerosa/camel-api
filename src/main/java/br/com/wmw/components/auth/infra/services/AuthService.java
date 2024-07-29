package br.com.wmw.components.auth.infra.services;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import br.com.wmw.components.auth.domain.services.IAuthService;
import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Singleton;

@Singleton
public class AuthService implements IAuthService {

    public String generateToken(String email, String coreUrl, String userId) {
        List<String> roles = new ArrayList<>();
        roles.add("USER");

        String token = Jwt.issuer("http://example.com/issuer")
                .upn(email)
                .expiresIn(Duration.ofHours(1))
                .claim("coreUrl", coreUrl)
                .claim("roles", roles)
                .claim("userId", userId)
                .sign();

        return token;
    }

    public boolean authenticate(String email, String password) {
        return email.equals(password);
    }
}
