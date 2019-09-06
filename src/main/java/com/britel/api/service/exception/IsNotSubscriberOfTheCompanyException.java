package com.britel.api.service.exception;

/**
 * @author Jhonny Vargas.
 */

@SuppressWarnings("serial")
public class IsNotSubscriberOfTheCompanyException extends Exception {
  public IsNotSubscriberOfTheCompanyException(String msg) {
    super(msg);
  }
}
