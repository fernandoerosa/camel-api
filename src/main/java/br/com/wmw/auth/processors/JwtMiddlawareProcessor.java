package br.com.wmw.auth.processors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.eclipse.microprofile.jwt.JsonWebToken;

import io.smallrye.jwt.auth.principal.DefaultJWTParser;
import io.smallrye.jwt.auth.principal.JWTParser;

public class JwtMiddlawareProcessor implements Processor {

    private final List<String> permission;

    public JwtMiddlawareProcessor(List<String> permission) {
        this.permission = permission;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        String authHeader = exchange.getIn().getHeader("Authorization", String.class);
        if (authHeader == null) {
            throw new RuntimeException("Token JWT não encontrado!");
        }
        
        JWTParser jwtParser = new DefaultJWTParser();

        // remove parseOnly
        JsonWebToken jsonWebToken = jwtParser.parseOnly(authHeader);

        Map<String, Object> body = new HashMap<>();
        body.put("permissions", permission);

        body.put("email", jsonWebToken.getClaim("upn"));

        exchange.getIn().setBody(body.toString());
    }

}
