package br.com.wmw.auth.processors;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import br.com.wmw.auth.helper.JwtService;

public class JwtAuthProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        Map<String, String> body = exchange.getIn().getBody(Map.class);

        String email = body.get("email");
        String password = body.get("password");

        if (JwtService.authenticate(email, password)) {
            String token = JwtService.generateToken(email);
            exchange.getIn().setBody("{ \"token\": \"" + token + "\" }");
        } else {
            exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 401);
            exchange.getIn().setBody("Credenciais inválidas!");
        }
    }

}
