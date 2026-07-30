package com.dbtraining.reconx.domain;

public class TradeNotFoundException extends RuntimeException {
  public TradeNotFoundException(String message) {
    super(message);
  }
}
