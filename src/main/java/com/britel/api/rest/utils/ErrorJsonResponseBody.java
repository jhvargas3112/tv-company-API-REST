package com.britel.api.rest.utils;

import org.springframework.http.HttpStatus;

/**
 * @author Jhonny Vargas.
 */

public class ErrorJsonResponseBody {
  private Integer status;
  private String error;
  private String message;

  public ErrorJsonResponseBody(Integer status, String error, String message) {
    this.status = status;
    this.error = error;
    this.message = message;
  }

  public static ErrorJsonResponseBody buildUnprocessableEntityJsonBody() {
    return new ErrorJsonResponseBody(HttpStatus.UNPROCESSABLE_ENTITY.value(), HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(), null);
  }

  public static ErrorJsonResponseBody buildUnprocessableEntityJsonBody(String message) {
    return new ErrorJsonResponseBody(HttpStatus.UNPROCESSABLE_ENTITY.value(), HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(), message);
  }

  public static ErrorJsonResponseBody buildForbiddenJsonBody() {
    return new ErrorJsonResponseBody(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase(), null);
  }

  public static ErrorJsonResponseBody buildForbiddenJsonBody(String message) {
    return new ErrorJsonResponseBody(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase(), message);
  }

  public static ErrorJsonResponseBody buildLockedJsonBody() {
    return new ErrorJsonResponseBody(HttpStatus.LOCKED.value(), HttpStatus.LOCKED.getReasonPhrase(), null);
  }

  public static ErrorJsonResponseBody buildLockedJsonBody(String message) {
    return new ErrorJsonResponseBody(HttpStatus.LOCKED.value(), HttpStatus.LOCKED.getReasonPhrase(), message);
  }

  public ErrorJsonResponseBody addMessage(String message) {
    this.setMessage(message);
    return this;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
