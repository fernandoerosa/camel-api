package br.com.wmw.auth.processors;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class OrderProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        Map<String, Object> legacyOrder = exchange.getIn().getBody(Map.class);

        Map<String, Object> newOrder = new HashMap<>();
        newOrder.put("cartId", legacyOrder.get("numeroCarrinho"));
        newOrder.put("userId", legacyOrder.get("numeroUsuario"));

        exchange.getIn().setBody(newOrder);
    }
}