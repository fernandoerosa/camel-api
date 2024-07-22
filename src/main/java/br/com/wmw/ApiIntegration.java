package br.com.wmw;

import java.util.Arrays;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import br.com.wmw.auth.processors.JwtAuthProcessor;
import br.com.wmw.auth.processors.JwtMiddlawareProcessor;
import br.com.wmw.auth.processors.OrderProcessor;

public class ApiIntegration extends RouteBuilder {  

    @Override
    public void configure() throws Exception {

        from("rest:post:/token")
            .unmarshal().json(JsonLibrary.Jackson)
            .process(new JwtAuthProcessor());

        from("rest:post:/order/create")
            .process(new JwtMiddlawareProcessor(Arrays.asList("CREATE_ORDER")))
            .choice()
            .when(simple("${header.legacy} == 'true'"))
                .to("direct:legacy-orders")
            .otherwise()
                .to("direct:createOrder");
        
        from("direct:legacy-orders")
            .unmarshal().json(JsonLibrary.Jackson)
            .process(new OrderProcessor())
            .marshal().json(JsonLibrary.Jackson)
            .to("direct:createOrder");

        from("direct:createOrder")
            .toD("http:${header.core}?bridgeEndpoint=true");
    }
}
