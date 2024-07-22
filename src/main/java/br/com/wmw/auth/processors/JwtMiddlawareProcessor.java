package br.com.wmw.auth.processors;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.eclipse.microprofile.jwt.JsonWebToken;

import io.smallrye.jwt.auth.principal.DefaultJWTParser;
import io.smallrye.jwt.auth.principal.JWTParser;
import jakarta.json.JsonString;

public class JwtMiddlawareProcessor implements Processor {

    private final List<String> permissions;

    public JwtMiddlawareProcessor(List<String> permission) {
        this.permissions = permission;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        String authHeader = exchange.getIn().getHeader("Authorization", String.class);
        if (authHeader == null) {
            throw new RuntimeException("Token JWT não encontrado!");
        }
        
        JWTParser jwtParser = new DefaultJWTParser();

        JsonWebToken jsonWebToken = jwtParser.parseOnly(authHeader);

        List<String> roles = extractRoles(jsonWebToken.getClaim("roles"));

        if (!hasPermissions(roles)) {
            throw new Exception("Sem Permissão");
        }
    }

    private List<String> extractRoles(Object rolesClaim) {
        if (rolesClaim instanceof List<?>) {
            List<?> roles = (List<?>) rolesClaim;
            if (!roles.isEmpty() && roles.get(0) instanceof JsonString) {
                return roles.stream()
                            .map(role -> ((JsonString) role).getString())
                            .toList();
            }
            return (List<String>) rolesClaim;
        }

        return Collections.emptyList();
    }

    private boolean hasPermissions(List<String> roles) {
        Boolean hasPermisson = false;

        // Desacoplar
        Map<String, List<String>> realRoles = new HashMap<>();
        realRoles.put("USER", Arrays.asList("CREATE_ORDER", "LOGIN"));

        for (String role : roles) {
            List<String> permissionsOfRole = realRoles.get(role);
            hasPermisson = permissionsOfRole.containsAll(permissions);
        }
        
        return hasPermisson;
    }
}
