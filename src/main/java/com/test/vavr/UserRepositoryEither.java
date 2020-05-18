package com.test.vavr;

import io.vavr.control.Either;

public class UserRepositoryEither {

  public static Either<String, User> getUser(String username) {
    if (username.isEmpty()) {
      return Either.left("No such user");
    }
    return Either.right(new User(username));
  }
}
