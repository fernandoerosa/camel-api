package br.com.wmw.web;

import java.util.Arrays;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import br.com.wmw.components.auth.processors.PermissionMiddlawareProcessor;
import br.com.wmw.components.transform.processors.OrderProcessor;
import br.com.wmw.components.user.domain.repositories.IUserRepository;
import br.com.wmw.components.user.infra.repositories.MongoUserRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class OrderRouter extends RouteBuilder {  

    private final IUserRepository userRepository;

    @Inject
    public OrderRouter(MongoUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void configure() throws Exception {

          from("rest:post:/order/create")
            .process(new PermissionMiddlawareProcessor(Arrays.asList("CREATE_ORDER"), this.userRepository))
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
