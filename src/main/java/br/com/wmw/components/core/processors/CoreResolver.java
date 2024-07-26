package br.com.wmw.components.core.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class CoreResolver implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        String camelUrl = exchange.getIn().getHeader("CamelHttpUrl", String.class);
        String url = camelUrl.split("/")[2];

        // Implantar service para capturar pelo mongo
        if (url.equals("localhost:8080")) {
            exchange.getIn().setHeader("coreUrl", "localhost:8081");
        } else if (url.equals("localhost:8082")) {
            exchange.getIn().setHeader("coreUrl", "localhost:8083");
        }
    }
}
