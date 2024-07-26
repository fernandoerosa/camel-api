package br.com.wmw.components.user.infra.repositories;

import br.com.wmw.components.user.domain.entity.User;
import br.com.wmw.components.user.domain.repositories.IUserRepository;
import jakarta.inject.Singleton;

@Singleton
public class MongoUserRepository implements IUserRepository {

    @Override
    public User getUser(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUser'");
    }
}
