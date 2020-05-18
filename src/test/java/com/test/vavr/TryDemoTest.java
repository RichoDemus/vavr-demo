package com.test.vavr;


import static com.test.vavr.UserRepositoryTry.getUser;

import io.vavr.control.Try;
import javax.naming.ServiceUnavailableException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TryDemoTest {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Test
  public void basic_usage() {
    Try<User> success = getUser("Richo");
    assert success.isSuccess();
    assert success.get().username.equals("Richo");

    Try<User> failure = getUser("");
    assert failure.isFailure();
    Throwable cause = failure.getCause();
    assert cause.getMessage().equals("No such user");
  }

  @Test
  public void fallback_value_turn_failure_to_success() {
    Try<User> userTry = getUser("");
    assert userTry.isFailure();

    Try<User> recovered = userTry
        .recover(ServiceUnavailableException.class, new User("Default User"))
        .recover(e -> {
          logger.warn("Got exception {}, falling back to default user", e.getMessage());
          return new User("Also Default User");
        });

    assert recovered.isSuccess();
    User user = recovered.get();
    assert user.username.equals("Also Default User");
  }

  @Test
  public void validate_turn_success_to_failure() {
    Try<User> userTry = getUser("Banned Bert")
        .filter(
            user -> !user.username.equals("Banned Bert"), // predicate to validate
            user -> new Exception(user.username + " is banned") // Left value if predicate is false
        );
    assert userTry.isFailure();
    Throwable cause = userTry.getCause();
    assert cause.getMessage().equals("Banned Bert is banned");
  }
}
