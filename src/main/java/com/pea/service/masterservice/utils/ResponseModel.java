package com.pea.service.masterservice.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseModel {
  private Object result;
  private String statusDescription;
  private String status;
  private int statusCode;
}
