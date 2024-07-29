package br.com.wmw.components.core.infra.repositories;

import br.com.wmw.components.core.domain.entity.Core;
import br.com.wmw.components.core.domain.repositories.ICoreRepository;
import br.com.wmw.components.core.infra.entity.MongoCore;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.inject.Singleton;

@Singleton
public class MongoCoreRepository implements ICoreRepository, PanacheMongoRepository<MongoCore> {

    @Override
    public Core getCoreByClientUrl(String clientUrl) {
        MongoCore mongoCore = this.find("clientUrl", clientUrl).firstResult();
        return new Core(mongoCore.getId().toString(), mongoCore.getUrl(), mongoCore.getCustomerId(), mongoCore.getClientUrl());
    }
}
