package com.example.circutbreaker.service;

public class TestCustomException extends RuntimeException{
  public TestCustomException(String message){
    super(message);
  }
}
