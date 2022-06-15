package com.pea.service.masterservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pea.service.masterservice.constant.MassageError;
import com.pea.service.masterservice.constant.StatusResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: Saknarin Aranmala (Toffee)
 * @since: Nov 15, 2019
 * @author refer Sompong
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class ResponseUtils {

  private Integer responseCode;
  private String responseStatus;
  private String responseMessage;
  private Object result;

  public static ResponseUtils result(Object o) {
    return ResponseUtils.builder()
        .result(o)
        .responseCode(200)
        .responseStatus(StatusResponse.SUCCESS.getValue())
        .responseMessage("Get Data Success")
        .build();
  }

  public static ResponseUtils custom(Object o, String msg) {
    return ResponseUtils.builder()
        .result(o)
        .responseCode(200)
        .responseStatus(StatusResponse.SUCCESS.getValue())
        .responseMessage(msg)
        .build();
  }

  public static ResponseUtils create() {
    return ResponseUtils.builder()
        .responseCode(201)
        .responseStatus(StatusResponse.SUCCESS.getValue())
        .responseMessage("Created Success")
        .build();
  }

  public static ResponseUtils update() {
    return ResponseUtils.builder()
        .responseCode(200)
        .responseStatus(StatusResponse.SUCCESS.getValue())
        .responseMessage("Updated Success")
        .build();
  }

  public static ResponseUtils delete() {
    return ResponseUtils.builder()
        .responseCode(201)
        .responseStatus(StatusResponse.SUCCESS.getValue())
        .responseMessage("Delete Success")
        .build();
  }

  public static ResponseUtils notFound(String msg) {
    return ResponseUtils.builder()
        .responseCode(404)
        .responseStatus(MassageError.ERM0001)
        .responseMessage(msg)
        .build();
  }

  public static ResponseUtils conflict(String msg) {
    return ResponseUtils.builder()
        .responseCode(409)
        .responseStatus(MassageError.ERM0002)
        .responseMessage(msg)
        .build();
  }

  public static void printJsonFormat(Object object) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    } catch (JsonProcessingException e) {
      log.error("printJsonFormat"+e.getMessage());
    }
  }
}
