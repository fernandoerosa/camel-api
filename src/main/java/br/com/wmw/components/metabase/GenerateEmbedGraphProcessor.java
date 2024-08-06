package br.com.wmw.components.metabase;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class GenerateEmbedGraphProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        Map<String, Object> body = exchange.getIn().getBody(Map.class);
        String url = new MetaBaseService().getIframeUrl(Integer.parseInt(body.get("userId").toString()));
        exchange.getIn().setBody("{ \"url\": \"" + url + "\" }");
    }
}
