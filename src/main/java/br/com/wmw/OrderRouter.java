package br.com.wmw;

import java.util.Arrays;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import br.com.wmw.auth.processors.JwtMiddlawareProcessor;
import br.com.wmw.transforms.OrderProcessor;;

public class OrderRouter extends RouteBuilder {  

    @Override
    public void configure() throws Exception {

          from("rest:post:/order/create")
            .process(new JwtMiddlawareProcessor(Arrays.asList("CREATE_ORDER")))
            .choice()
            .when(simple("${header.legacy} == 'true'"))
                .to("direct:legacy-orders")
            .otherwise()
                .to("direct:createOrder");
        
        from("direct:legacy-orders")
            .unmarshal().json(JsonLibrary.Jackson)  
            .log("Legacy Body: ${body}")
            .process(new OrderProcessor())
            .marshal().json(JsonLibrary.Jackson)
            .log("Legacy Body Parsed: ${body}")
            .to("direct:createOrder");

        from("direct:createOrder")
            .toD("http:${header.coreUrl}?bridgeEndpoint=true");
    }
}
