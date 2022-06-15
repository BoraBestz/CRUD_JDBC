package com.pea.service.masterservice.constant;

public enum StatusResponse {
  SUCCESS("Success"),
  FAIL("Failure");

  private String value;

  StatusResponse(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
