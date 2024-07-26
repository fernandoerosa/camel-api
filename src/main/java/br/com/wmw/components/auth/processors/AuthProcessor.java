package br.com.wmw.components.auth.processors;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import br.com.wmw.components.auth.domain.services.IAuthService;
import br.com.wmw.components.user.domain.entity.User;
import br.com.wmw.components.user.domain.repositories.IUserRepository;

public class AuthProcessor implements Processor {

    private final IAuthService authService;
    private final IUserRepository userRepository;

    public AuthProcessor(IAuthService authService, IUserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @Override
    public void process(Exchange exchange) throws Exception {

        Map<String, String> body = exchange.getIn().getBody(Map.class);

        String email = body.get("email");
        String password = body.get("password");

        User user = this.userRepository.getUser(email);

        if (this.authService.authenticate(user.getPassword(), password)) {
            String token = this.authService.generateToken(user.getEmail(), user.getCoreUrl());
            exchange.getIn().setBody("{ \"token\": \"" + token + "\" }");
        } else {
            exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 401);
            exchange.getIn().setBody("Credenciais inválidas!");
        }
    }
}
