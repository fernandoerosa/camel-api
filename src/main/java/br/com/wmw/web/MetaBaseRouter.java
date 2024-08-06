package br.com.wmw.web;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import br.com.wmw.components.metabase.GenerateEmbedGraphProcessor;

public class MetaBaseRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("rest:post:/metabase")
            .unmarshal().json(JsonLibrary.Jackson)
            .process(new GenerateEmbedGraphProcessor());   
    }
}
