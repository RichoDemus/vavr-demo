package com.test.vavr;

import static com.test.vavr.UserRepositoryEither.getUser;

import io.vavr.control.Either;
import org.junit.Test;

public class EitherDemoTest {

  @Test
  public void basicUsage() {
    Either<String, User> either = getUser("Richo");
    assert either.isRight();
    User user = either.get();
    assert user.username.equals("Richo");

    Either<String, User> either2 = getUser("");
    assert either2.isLeft();
    String msg = either2.getLeft();
    assert msg.equals("No such user");
  }

  @Test
  public void fallback_value_turn_left_to_right() {
    Either<String, User> either = getUser("")
        .orElse(
            Either.right(new User("Default User"))
        );

    assert either.isRight();
    User user = either.get();
    assert user.username.equals("Default User");
  }

  @Test
  public void validateEither_turn_right_to_left() {
    Either<String, User> either = getUser("Banned Bert")
        .filterOrElse(
            user -> !user.username.equals("Banned Bert"), // predicate to validate
            user -> user.username + " is banned" // Left value if predicate is false
        );
    assert either.isLeft();
    assert either.getLeft().equals("Banned Bert is banned");
  }

  @Test
  public void looks_like_optional() {
    Either<String, User> user = getUser("Richo");

    // map method works just like Optionals map and is applied on the right value
    Either<String, String> username = user.map(u -> u.username);

    // there's also a map left that is applied on the left value
    String myError = Either.left("my error")
        .mapLeft(String::toUpperCase)
        .getLeft();
    assert myError.equals("MY ERROR");

    // has orElseThrow just like optional, it lets you construct an exception from the left value
    user.getOrElseThrow(leftMsg -> new IllegalStateException("bla bla: " + leftMsg));

    // also has some overkill functions for like providing mapping functions for left and right at the same time
    Either<String, String> mapped = user.bimap(
        leftValue -> leftValue.toUpperCase(),
        rightValue -> rightValue.username
    );
  }
}
