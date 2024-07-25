package br.com.wmw;

import java.util.Arrays;

import org.apache.camel.builder.RouteBuilder;

import br.com.wmw.auth.processors.JwtMiddlawareProcessor;
import br.com.wmw.infra.InfraResolver;

public class ProductRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("rest:post:/product")
            .process(new InfraResolver())
            .to("direct:product");

        from("rest:post:/secure-product")
            .process(new JwtMiddlawareProcessor(Arrays.asList("READ_PRODUCT")))
            .log("Body: ${body}")
            .log("Headers: ${header.coreUrl}")
            .to("direct:product");

        from("direct:product")
            .log("Body: ${body}")
            .log("Headers: ${header.coreUrl}")
            .toD("http:${header.coreUrl}/product?bridgeEndpoint=true");
    }
}
