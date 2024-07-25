package br.com.wmw;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import br.com.wmw.auth.processors.JwtAuthProcessor;

public class AuthRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("rest:post:/token")
            .unmarshal().json(JsonLibrary.Jackson)
            .process(new JwtAuthProcessor());
    }
}
