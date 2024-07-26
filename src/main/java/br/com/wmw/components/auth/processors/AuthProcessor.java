package br.com.wmw.components.auth.processors;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import br.com.wmw.components.auth.domain.services.IAuthService;

public class AuthProcessor implements Processor {

    private final IAuthService authService;

    public AuthProcessor(IAuthService authService) {
        this.authService = authService;
    }

    @Override
    public void process(Exchange exchange) throws Exception {

        Map<String, String> body = exchange.getIn().getBody(Map.class);

        String email = body.get("email");
        String password = body.get("password");

        if (this.authService.authenticate(email, password)) {
            String token = this.authService.generateToken(email, "http://localhost:8080");
            exchange.getIn().setBody("{ \"token\": \"" + token + "\" }");
        } else {
            exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 401);
            exchange.getIn().setBody("Credenciais inválidas!");
        }
    }

}
