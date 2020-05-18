package com.test.vavr;

import io.vavr.collection.List;
import io.vavr.collection.Map;
import org.junit.Test;

public class ListTest {

  @Test
  public void listTest() {

    List<String> uppercases = List.of("hello", "world").map(str -> str.toUpperCase());

    Map<String, List<User>> mapped = List.of(new User("Richo"), new User("Kalle"), new User("Anna"))
        .groupBy(user -> user.username);

    int sum = List.of(1, 2, 3, 4).fold(0, (acc, next) -> acc + next);
    int alsoSum = List.of(1, 2, 3).sum().intValue();

    java.util.List<String> strings = uppercases.asJava();
  }
}
