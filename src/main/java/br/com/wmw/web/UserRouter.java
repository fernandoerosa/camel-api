package br.com.wmw.web;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import br.com.wmw.components.user.domain.repositories.IUserRepository;
import br.com.wmw.components.user.infra.repositories.MongoUserRepository;
import br.com.wmw.components.user.processors.CreateUserProcessor;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class UserRouter extends RouteBuilder {

    private final IUserRepository userRepository;

    @Inject
    public UserRouter(MongoUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void configure() throws Exception {
        from("rest:post:/user/create")
            .unmarshal().json(JsonLibrary.Jackson)    
            .process(new CreateUserProcessor(this.userRepository))
            .marshal().json(JsonLibrary.Jackson);
    }
}
