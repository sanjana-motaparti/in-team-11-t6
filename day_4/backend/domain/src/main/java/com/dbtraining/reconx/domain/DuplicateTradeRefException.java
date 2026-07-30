package com.dbtraining.reconx.domain;

public class DuplicateTradeRefException extends RuntimeException {
  public DuplicateTradeRefException(String message) {
    super(message);
  }
}
