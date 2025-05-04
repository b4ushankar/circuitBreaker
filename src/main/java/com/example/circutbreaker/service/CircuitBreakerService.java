package com.example.circutbreaker.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
@Slf4j
public class CircuitBreakerService {

  private final RestTemplate restTemplate;


  @CircuitBreaker(name = "test", fallbackMethod = "fallbackMethod")
  public String testPublicUrl(){
    int number = ThreadLocalRandom.current().nextInt(1,50);
    if(number > 25 && number < 45)
      throw new TestCustomException("i am throwing exception");

    String url = "https://jsonplaceholder.typicode.com/posts/"+number;
    return restTemplate.getForObject(url,String.class);
  }

  public String fallbackMethod(TestCustomException exception){
    log.info("fallback response");
    return "fallback response";
  }

}
