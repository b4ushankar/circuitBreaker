package com.example.circutbreaker.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.event.*;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CircuitBreakerEventLogger {

  private final CircuitBreakerRegistry circuitBreakerRegistry;

  public CircuitBreakerEventLogger(CircuitBreakerRegistry circuitBreakerRegistry) {
    this.circuitBreakerRegistry = circuitBreakerRegistry;
  }

  @PostConstruct
  public void registerEventLogger() {
    CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("test");

    circuitBreaker.getEventPublisher()
        .onSuccess(event -> log.info("SUCCESS: {}", event))
        .onError(event -> log.warn("ERROR: {}", event))
        .onStateTransition(event -> log.info("STATE TRANSITION: {}", event.getStateTransition()))
        .onCallNotPermitted(event -> log.warn("CALL NOT PERMITTED: {}", event));
  }
}

