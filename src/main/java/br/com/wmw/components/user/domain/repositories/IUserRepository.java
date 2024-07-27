package br.com.wmw.components.user.domain.repositories;

import br.com.wmw.components.user.domain.entity.User;

public interface IUserRepository {
   public User getUser(String email);
   public User createUser(User user);
}
