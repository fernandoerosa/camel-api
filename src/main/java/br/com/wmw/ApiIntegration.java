package br.com.wmw;

import java.util.Collections;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import br.com.wmw.auth.processors.JwtAuthProcessor;
import br.com.wmw.auth.processors.JwtMiddlawareProcessor;

public class ApiIntegration extends RouteBuilder {  

    @Override
    public void configure() throws Exception {
        
        from("rest:post:/token")
            .unmarshal().json(JsonLibrary.Jackson)
            .process(new JwtAuthProcessor());

        from("rest:post:/order/create")
            .process(new JwtMiddlawareProcessor(Collections.singletonList("admin")));

        from("direct:createOrder")
            .toD("http:${header.core}?bridgeEndpoint=true");
    }
}
