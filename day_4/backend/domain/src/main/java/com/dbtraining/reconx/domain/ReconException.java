package com.dbtraining.reconx.domain;

public class ReconException extends RuntimeException {
  private final String reconBreakId;

  public ReconException(String message, String reconBreakId) {
    super(message);
    this.reconBreakId = reconBreakId;
  }

  public String getReconBreakId() {
    return reconBreakId;
  }
}
