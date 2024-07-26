package br.com.wmw.web;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import br.com.wmw.components.auth.domain.services.IAuthService;
import br.com.wmw.components.auth.infra.services.AuthService;
import br.com.wmw.components.auth.processors.AuthProcessor;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class AuthRouter extends RouteBuilder {

    private final IAuthService authService;

    @Inject
    public AuthRouter(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void configure() throws Exception {
        from("rest:post:/token")
            .unmarshal().json(JsonLibrary.Jackson)
            .process(new AuthProcessor(authService));
    }
}
