package br.com.wmw.components.user.domain.repositories;

import br.com.wmw.components.user.domain.entity.User;

public interface IUserRepository {
   public User getUserByEmail(String email);
   public User getUserById(String userId);
   public User createUser(User user);
}
