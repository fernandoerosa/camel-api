package br.com.wmw.components.user.processors;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import br.com.wmw.components.user.domain.entity.User;
import br.com.wmw.components.user.domain.repositories.IUserRepository;

public class CreateUserProcessor implements Processor {

    private final IUserRepository userRepository;

    public CreateUserProcessor(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        Map<String, Object> user = exchange.getIn().getBody(Map.class);

        this.userRepository.createUser(new User("", user.get("email").toString(), user.get("password").toString(), user.get("customerId").toString(), user.get("coreUrl").toString()));
    }    
}
