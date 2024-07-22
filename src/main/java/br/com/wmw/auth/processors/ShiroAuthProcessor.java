package br.com.wmw.auth.processors;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.shiro.security.ShiroSecurityToken;
import org.apache.camel.component.shiro.security.ShiroSecurityTokenInjector;

public class ShiroAuthProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
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
    }

}
