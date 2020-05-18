package com.test.vavr;

import io.vavr.control.Try;

public class UserRepositoryTry {

  public static Try<User> getUser(String username) {
    return Try.of(() -> getUserException(username));
  }

  static User getUserException(String username) throws Exception {
    if (username.isEmpty()) {
      throw new Exception("No such user");
    }
    return new User(username);
  }
}
