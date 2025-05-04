package com.example.circutbreaker.controller;

import com.example.circutbreaker.service.CircuitBreakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CircuitBreakerController {

  private final CircuitBreakerService circuitBreakerService;

  @GetMapping("/v1/circuitbreaker")
  public String testCircuitBreaker(){
    String response = circuitBreakerService.testPublicUrl();
    log.debug("good morning circuit breaker {}",response);
    return "Good morning circuitBreaker "+ response;
  }


}
