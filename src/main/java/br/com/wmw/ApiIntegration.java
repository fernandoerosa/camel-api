package br.com.wmw;

import java.util.Map;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.shiro.security.ShiroSecurityToken;
import org.apache.camel.component.shiro.security.ShiroSecurityTokenInjector;
import org.apache.camel.model.dataformat.JsonLibrary;

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

        from("rest:post:/token")
            .unmarshal().json(JsonLibrary.Jackson)
            .process(exchange -> {
                Map<String, String> body = exchange.getIn().getBody(Map.class);

                String username = body.get("username");
                String password = body.get("password");

                ShiroSecurityToken shiroSecurityToken = new ShiroSecurityToken(username, password);

                final byte[] passPhrase = {
                        (byte) 0x08, (byte) 0x09, (byte) 0x0A, (byte) 0x0B,
                        (byte) 0x0C, (byte) 0x0D, (byte) 0x0E, (byte) 0x0F,
                        (byte) 0x10, (byte) 0x11, (byte) 0x12, (byte) 0x13,
                        (byte) 0x14, (byte) 0x15, (byte) 0x16, (byte) 0x17
                };

                ShiroSecurityTokenInjector shiroSecurityTokenInjector = new ShiroSecurityTokenInjector(
                        shiroSecurityToken, passPhrase);

                shiroSecurityTokenInjector.process(exchange);
            })
            .log("${headers.SHIRO_SECURITY_TOKEN}")
            .setBody(simple("{\"token\": \"${headers.SHIRO_SECURITY_TOKEN}\"}"));
    }
}
