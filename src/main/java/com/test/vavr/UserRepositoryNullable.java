package com.test.vavr;

public class UserRepositoryNullable {

  static User getUser(String username) {
    if (username.isEmpty()) {
      return null;
    }
    return new User(username);
  }
}
