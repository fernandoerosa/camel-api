package br.com.wmw.components.auth.processors;

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

public class PermissionMiddlawareProcessor implements Processor {

    private final List<String> permissions;

    public static PermissionMiddlawareProcessor withoutPermissions() {
        return new PermissionMiddlawareProcessor(Collections.emptyList());
    }

    public PermissionMiddlawareProcessor(List<String> permissions) {
        this.permissions = permissions;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        String authHeader = exchange.getIn().getHeader("Authorization", String.class);
        if (authHeader == null) {
            throw new Exception("Token JWT não encontrado!");
        }
        
        JWTParser jwtParser = new DefaultJWTParser();

        JsonWebToken jsonWebToken = jwtParser.parseOnly(authHeader);

        //Capturar roles por usuario
        List<String> roles = extractRoles(jsonWebToken.getClaim("roles"));

        if (! this.permissions.isEmpty() || hasPermissions(roles)) {
            exchange.getIn().setHeader("coreUrl", jsonWebToken.getClaim("coreUrl"));
        } else {
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
        realRoles.put("USER", Arrays.asList("CREATE_ORDER", "LOGIN", "READ_PRODUCT"));

        for (String role : roles) {
            List<String> permissionsOfRole = realRoles.get(role);
            hasPermisson = permissionsOfRole.containsAll(permissions);
        }
        
        return hasPermisson;
    }
}
