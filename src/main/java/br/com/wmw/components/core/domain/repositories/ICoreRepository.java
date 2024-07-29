package br.com.wmw.components.core.domain.repositories;

import br.com.wmw.components.core.domain.entity.Core;

public interface ICoreRepository {
    public Core getCoreByClientUrl(String clientUrl);
}
