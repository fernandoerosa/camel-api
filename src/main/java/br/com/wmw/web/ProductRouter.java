package br.com.wmw.web;

import java.util.Arrays;

import org.apache.camel.builder.RouteBuilder;

import br.com.wmw.components.auth.processors.PermissionMiddlawareProcessor;
import br.com.wmw.components.core.domain.repositories.ICoreRepository;
import br.com.wmw.components.core.infra.repositories.MongoCoreRepository;
import br.com.wmw.components.core.processors.CoreResolver;
import br.com.wmw.components.user.domain.repositories.IUserRepository;
import br.com.wmw.components.user.infra.repositories.MongoUserRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class ProductRouter extends RouteBuilder {

    private final ICoreRepository coreRepository;
    private final IUserRepository userRepository;

    @Inject
    public ProductRouter(MongoCoreRepository coreRepository, MongoUserRepository userRepository) {
        this.coreRepository = coreRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void configure() throws Exception {
        from("rest:post:/product")
            .choice()
            .when(simple("${header.Authorization} == null"))
                .process(new CoreResolver(this.coreRepository))
                .log("Core resolver Headers: ${header.coreUrl}")
                .to("direct:product")
            .otherwise()
                .process(PermissionMiddlawareProcessor.withoutPermissions(this.userRepository))
                .log("Middleware Headers: ${header.coreUrl}")
                .to("direct:product");

        from("rest:post:/secure-product")
            .process(new PermissionMiddlawareProcessor(Arrays.asList("READ_PRODUCT"), this.userRepository))
            .log("Body: ${body}")
            .log("Headers: ${header.coreUrl}")
            .to("direct:product");

        from("direct:product")
            .log("Body: ${body}")
            .log("Headers: ${header.coreUrl}")
            .toD("http:${header.coreUrl}/product?bridgeEndpoint=true");
    }
}
