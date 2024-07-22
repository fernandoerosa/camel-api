package br.com.wmw;

import org.apache.camel.builder.RouteBuilder;

import br.com.wmw.auth.processors.AuthProcessor;

public class ApiIntegration extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("rest:post:/order/create")
            .log("Header: ${header.core}")
            .to("direct:createOrder");
        
        from("direct:createOrder")
            .process(new AuthProcessor())
            .toD("http:${header.core}?bridgeEndpoint=true");
    }
}
