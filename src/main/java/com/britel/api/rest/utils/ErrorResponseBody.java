package com.britel.api.rest.utils;

/**
 * @author Jhonny Vargas.
 */

public class ErrorResponseBody {
  private Integer status;
  private String error;
  private String message;

  public ErrorResponseBody(Integer status, String error, String message) {
    super();

    this.status = status;
    this.error = error;
    this.message = message;
  }

  public ErrorResponseBody addMessage(String message) {
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
