package com.example.circutbreaker.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.control.Try;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CircuitBreakerServiceTest {

  @Autowired
  CircuitBreakerRegistry circuitBreakerRegistry;

  private CircuitBreaker circuitBreaker;

  @BeforeEach
  void setUp() {
    circuitBreaker = circuitBreakerRegistry.circuitBreaker("test");
    circuitBreaker.reset(); // Ensure a fresh state before each test
  }

  @Test
  void testCircuitBreakerTransitions() {

        for (int i = 0; i < 10; i++) {
      Try.run(() -> circuitBreaker.executeRunnable(() -> {
        throw new RuntimeException("Failing logic");
      })).onFailure(e -> {
        System.out.println("Caught: " + e.getMessage());
      });
    }

    assertThat(circuitBreaker.getState()).isEqualTo(CircuitBreaker.State.OPEN);
  }

  @Test
  void testCircuitBreakerAllowsSuccessfulCallInClosedState() {
    Try.run(() -> circuitBreaker.executeRunnable(() -> {
      // success logic
      System.out.println("Success call");
    }));

    assertThat(circuitBreaker.getState()).isEqualTo(CircuitBreaker.State.CLOSED);
  }

  @Test
  void testCallNotPermittedWhenCircuitBreakerIsOpen() {
    // Open the circuit breaker
    for (int i = 0; i < 5; i++) {
      Try.run(() -> circuitBreaker.executeRunnable(() -> {
        throw new RuntimeException("Fail");
      }));
    }

    // This call should be rejected
    Try<Void> result = Try.run(() -> circuitBreaker.executeRunnable(() -> {
      System.out.println("Should not run");
    }));

    assertThat(result.isFailure()).isTrue();
    assertThat(result.getCause().getMessage()).contains("not permit further calls");
  }

}