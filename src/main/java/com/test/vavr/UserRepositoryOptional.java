package com.test.vavr;

import java.util.Optional;

public class UserRepositoryOptional {

  public static Optional<User> getUser(String username) {
    if (username.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(new User(username));
  }
}
