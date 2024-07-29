package br.com.wmw.web;

import java.util.Arrays;

import org.apache.camel.builder.RouteBuilder;

import br.com.wmw.components.auth.processors.PermissionMiddlawareProcessor;
import br.com.wmw.components.core.domain.repositories.ICoreRepository;
import br.com.wmw.components.core.infra.repositories.MongoCoreRepository;
import br.com.wmw.components.core.processors.CoreResolver;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class ProductRouter extends RouteBuilder {

    private final ICoreRepository coreRepository;

    @Inject
    public ProductRouter(MongoCoreRepository coreRepository) {
        this.coreRepository = coreRepository;
    }

    @Override
    public void configure() throws Exception {
        from("rest:post:/product")
            .process(new CoreResolver(this.coreRepository))
            .log("Headers: ${header.coreUrl}")
            .to("direct:product");

        from("rest:post:/secure-product")
            .process(new PermissionMiddlawareProcessor(Arrays.asList("READ_PRODUCT")))
            .log("Body: ${body}")
            .log("Headers: ${header.coreUrl}")
            .to("direct:product");

        from("direct:product")
            .log("Body: ${body}")
            .log("Headers: ${header.coreUrl}")
            .toD("http:${header.coreUrl}/product?bridgeEndpoint=true");
    }
}
