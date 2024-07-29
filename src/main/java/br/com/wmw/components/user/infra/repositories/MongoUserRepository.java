package br.com.wmw.components.user.infra.repositories;

import org.bson.types.ObjectId;

import br.com.wmw.components.user.domain.entity.User;
import br.com.wmw.components.user.domain.repositories.IUserRepository;
import br.com.wmw.components.user.infra.entity.MongoUser;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.inject.Singleton;

@Singleton
public class MongoUserRepository implements IUserRepository, PanacheMongoRepository<MongoUser> {

    @Override
    public User getUserByEmail(String email) {
        MongoUser mongoUser = find("email", email).firstResult();

        if (mongoUser == null) {
            throw new RuntimeException("User not found");
        }
        
        return new User(mongoUser.getId().toString(), mongoUser.getEmail(), mongoUser.getPassword(), mongoUser.getCustomerId(), mongoUser.getCoreUrl());
    }

    @Override
    public User createUser(User user) {
        MongoUser mongoUser = new MongoUser();
        mongoUser.setEmail(user.getEmail());
        mongoUser.setPassword(user.getPassword());
        mongoUser.setCustomerId(user.getCustomerId());
        mongoUser.setCoreUrl(user.getCoreUrl());

        this.persist(mongoUser);

        MongoUser createdUser = find("email", user.getEmail()).firstResult();

        return new User(createdUser.getId().toString(), createdUser.getEmail(), createdUser.getPassword(), createdUser.getCustomerId(), createdUser.getCoreUrl());
    }

    @Override
    public User getUserById(String userId) {
        MongoUser mongoUser = this.findById(new ObjectId(userId));

        if (mongoUser == null) {
            throw new RuntimeException("User not found");
        }

        return new User(mongoUser.getId().toString(), mongoUser.getEmail(), mongoUser.getPassword(), mongoUser.getCustomerId(), mongoUser.getCoreUrl());
    }
}
