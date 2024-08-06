package br.com.wmw.components.metabase;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Singleton;

@Singleton
public class MetaBaseService {

    private static final String METABASE_SITE_URL = "http://localhost:3000";
    private static final String METABASE_SECRET_KEY = "f6c9db721a32a699190d08c8a95513783f6d79a467d78a6ab6ffa0a9eb687e4d";

    private String generateToken(int userId) {
        Map<String, Object> resource = new HashMap<>();
        resource.put("dashboard", 2);

        Map<String, Object> params = new HashMap<>();

        Map<String, Object> claims = new HashMap<>();
        claims.put("resource", resource);
        claims.put("params", params);
        claims.put("exp", Instant.now().getEpochSecond() + (100000 * 60));

        String token = Jwt.claims(claims)
                .signWithSecret(METABASE_SECRET_KEY);

        return token;
    }

    public String getIframeUrl(int userId) {
        String token = generateToken(userId);
        return METABASE_SITE_URL + "/embed/dashboard/" + token + "#bordered=true&titled=true";
    }
}
