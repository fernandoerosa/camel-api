package br.com.wmw.components.core.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import br.com.wmw.components.core.domain.entity.Core;
import br.com.wmw.components.core.domain.repositories.ICoreRepository;

public class CoreResolver implements Processor {

    private final ICoreRepository coreRepository;

    public CoreResolver(ICoreRepository coreRepository) {
        this.coreRepository = coreRepository;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        String camelUrl = exchange.getIn().getHeader("CamelHttpUrl", String.class);
        String url = camelUrl.split("/")[2];

        Core core = this.coreRepository.getCoreByClientUrl(url);
        
        if (core == null) {
            throw new Exception("Core not found");
        }

        exchange.getIn().setHeader("coreUrl", core.getUrl());
    }
}
