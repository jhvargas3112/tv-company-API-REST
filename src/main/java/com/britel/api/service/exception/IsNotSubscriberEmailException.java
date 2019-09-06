package com.britel.api.service.exception;

@SuppressWarnings("serial")
public class IsNotSubscriberEmailException extends Exception {
  public IsNotSubscriberEmailException(String msg) {
    super(msg);
  }
}
