package com.britel.api.rest;

import java.net.URI;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.mail.EmailException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.britel.api.conf.utils.Constants;
import com.britel.api.model.User;
import com.britel.api.rest.utils.ErrorJsonResponseBody;
import com.britel.api.rest.utils.ErrorResponseBody;
import com.britel.api.rest.utils.ErrorResponseMsg;
import com.britel.api.service.CompanyAccountService;
import com.britel.api.service.SubscriberAccountService;
import com.britel.api.service.exception.IsNotSubscriberEmailException;
import com.britel.api.service.exception.IsNotSubscriberOfTheCompanyException;
import com.britel.api.service.utils.ExceptionMsg;

/**
 * @author Jhonny Vargas.
 */

@RestController
public class SubscriberAccountRestController {
  @Autowired
  private CompanyAccountService companyAccountService;
  @Autowired
  private SubscriberAccountService subscriberAccountService;

  /**
   * Gets the subscriber account information corresponding with the given email.
   * 
   * @param request
   * @param email
   * @return ResponseEntity - subscriber account information.
   */
  @GetMapping(value = "/api/subscriber/account/by_email/{email}")
  public ResponseEntity<Object> getSubscriberByEmail(HttpServletRequest request,
      @PathVariable(value = "email", required = true) String email) {
    User company = companyAccountService.findByCompanyJwt(getJwt(request));

    if (company == null) {
      return ResponseEntity.noContent().build();
    } else {
      User subscriber = subscriberAccountService.findByIdCompanyAndEmail(company.getIdUser(), email);

      if (subscriber == null)
        return ResponseEntity.noContent().build();
      else
        return ResponseEntity.ok(subscriber);
    }
  }

  /**
   * Gets the subscriber account information corresponding with the given id.
   * 
   * @param request
   * @param idSubscriber
   * @return ResponseEntity - subscriber account information.
   */
  @GetMapping(value = "/api/subscriber/account/by_id/{id_subscriber}")
  public ResponseEntity<Object> getSubscriberById(HttpServletRequest request,
      @PathVariable(value = "id_subscriber", required = true) Integer idSubscriber) {
    User company = companyAccountService.findByCompanyJwt(getJwt(request));

    if (company == null) {
      return ResponseEntity.noContent().build();
    } else {
      User subscriber = subscriberAccountService.findByIdCompanyAndIdUser(company.getIdUser(), idSubscriber);

      if (subscriber == null)
        return ResponseEntity.noContent().build();
      else
        return ResponseEntity.ok(subscriber);
    }
  }

  /**
   * Creates a new subscriber account for a specific company.
   * 
   * @param request
   * @param email
   * @param password
   * @param name
   * @param surname
   * @param city
   * @param country
   * @param address
   * @param postalCode
   * @return ResponseEntity
   */
  @PostMapping(value = "/api/subscriber/account/register")
  public ResponseEntity<Object> createSubscriberAccount(HttpServletRequest request,
      @RequestParam(value = "email", required = true) String email,
      @RequestParam(value = "password", required = true) String password,
      @RequestParam(value = "subscriber_type", required = true) String subscriberType,
      @RequestParam(value = "name", required = false) String name,
      @RequestParam(value = "surname", required = false) String surname,
      @RequestParam(value = "city", required = false) String city,
      @RequestParam(value = "country", required = false) String country,
      @RequestParam(value = "address", required = false) String address,
      @RequestParam(value = "postalcode", required = false) Integer postalCode) {

    if (subscriberAccountService.exists(email)) {
      return ResponseEntity.unprocessableEntity()
          .body(ErrorJsonResponseBody.buildUnprocessableEntityJsonBody(ErrorResponseMsg.EMAIL_ALREADY_EXISTS));
    }

    User company = companyAccountService.findByCompanyJwt(getJwt(request));

    User subscriber = User.buildSubscriberForBilling(email, password, subscriberType, company, city,country, address, postalCode, name, surname);

    ArrayList<Integer> organizations = new ArrayList<Integer>();
    organizations.add(14925); // 14925 by default for all the companies.

    subscriberAccountService.saveSubscriber(subscriber, organizations);

    return ResponseEntity.created(URI.create("/api/subscriber/account/by_id/" + subscriber.getIdUser().intValue())).build();
  }

  /**
   * Activates a subscriber account with the given email for a specific company.
   * 
   * @param email
   * @return ResponseEntity - if the request was success, then the response is the next renewal date of the subscriber account.
   */
  @PostMapping(value="/api/subscriber/account/activate")
  public ResponseEntity<Object> activateSubscriberAccount(HttpServletRequest request,
      @RequestParam(value = "email", required = true) String email) {
    if (!subscriberAccountService.exists(email)) {
      return ResponseEntity.unprocessableEntity().body(buildEmailNotExistsResponseBody());
    }

    int companyId = getCompanyId(request);

    Timestamp nextRenewal = null;

    try {
      nextRenewal = subscriberAccountService.getNextRenewal(companyId, email);
    } catch (IsNotSubscriberOfTheCompanyException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.LOCKED)
          .body(buildWrongSubscriberAccountResponseBody());
    }

    if (subscriberAccountService.findByEmail(email).getActive()) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .body(ErrorJsonResponseBody.buildForbiddenJsonBody(ErrorResponseMsg.SUBSCRIBER_IS_ACTIVE));
    }

    try {
      nextRenewal = subscriberAccountService.activateSubscriber(companyId, email);
    } catch (IsNotSubscriberOfTheCompanyException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.LOCKED)
          .body(buildWrongSubscriberAccountResponseBody());
    }

    JSONObject responseBody = new JSONObject();
    responseBody.put("next_renewal", nextRenewal);

    return ResponseEntity.ok(responseBody.toString());
  }

  /**
   * Sends a subscriber account deactivate request for a specific company with the given email.
   * 
   * @param email
   * @return ResponseEntity - if the request was success, then the response is the expiration date of the subscriber account.
   */
  @PostMapping(value="/api/subscriber/account/deactivate_request")
  public ResponseEntity<Object> deactivateSubscriberAccount(HttpServletRequest request,
      @RequestParam(value = "email", required = true) String email) {
    if (!subscriberAccountService.exists(email))
      return ResponseEntity.unprocessableEntity()
          .body(buildEmailNotExistsResponseBody());

    int companyId = getCompanyId(request);

    User subscriber = subscriberAccountService.findByEmail(email);
    Timestamp expirationDate = null;

    try {
      expirationDate = subscriberAccountService.getNextRenewal(companyId, email);
    } catch (IsNotSubscriberOfTheCompanyException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.LOCKED)
          .body(buildWrongSubscriberAccountResponseBody());
    }

    if (subscriber.getDeactivationRequest()) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .body(ErrorJsonResponseBody.buildForbiddenJsonBody(ErrorResponseMsg.DEACTIVATION_REQUEST_EXISTS));
    }

    try {
      subscriberAccountService.subscriberDeactivateRequest(companyId, email);
    } catch (IsNotSubscriberOfTheCompanyException e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.LOCKED)
          .body(buildWrongSubscriberAccountResponseBody());
    }

    JSONObject responseBody = new JSONObject();
    responseBody.put("expiration_date", expirationDate);

    return ResponseEntity.ok(responseBody.toString());
  }

  /**
   * Sends a password recovery code to the given email of the subscriber account.
   * 
   * @param email
   */
  @PostMapping(value = "/api/subscriber/account/recovery/send_code")
  public ResponseEntity<Object> sendPasswordRecoveryCode(@RequestParam(value = "email", required = true) String email) {
    try {
      subscriberAccountService.sendPasswordRecoveryCode(email);
    } catch (IsNotSubscriberEmailException e1) {
      e1.printStackTrace();
      return ResponseEntity.unprocessableEntity().body(ErrorJsonResponseBody.buildUnprocessableEntityJsonBody()
          .addMessage(ExceptionMsg.IS_NOT_SUBSCRIBER_EMAIL));
    } catch (EmailException e2) {
      e2.printStackTrace();
      return ResponseEntity.unprocessableEntity()
          .body(new ErrorResponseBody(HttpStatus.INTERNAL_SERVER_ERROR.value(),
              HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "Internal server error"));
    } 

    return ResponseEntity.ok(true);
  }

  /**
   * Changes the password to a subscriber with the given recovery code.
   * 
   * @param recoveryCode
   * @param newPassword
   */
  @PostMapping(value = "/api/subscriber/account/recovery/change_password")
  public ResponseEntity<Object> changePassword(@RequestParam(value = "recovery_code", required = true) Integer recoveryCode,
      @RequestParam(value = "new_password", required = true) String newPassword) {
    if (!subscriberAccountService.changePassword(recoveryCode, newPassword))
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .body(ErrorJsonResponseBody.buildForbiddenJsonBody(ErrorResponseMsg.WRONG_PASSWORD_RECOVERY_CODE));
    else
      return ResponseEntity.ok(true);
  }

  // Private methods

  /**
   * Returns the id of the company that has sent the HTTP request.
   * 
   * @param request - the current HTTP request.
   * @return The company's id.
   */
  private Integer getCompanyId(HttpServletRequest request) {
    return companyAccountService.findByCompanyJwt(getJwt(request)).getIdUser();
  }

  /**
   * Returns the JWT (JSON Web Token) of the company that has sent the HTTP request.
   * 
   * @param request - the current HTTP request.
   * @return The JWT (JSON Web Token) assigned to the company.
   */
  private String getJwt(HttpServletRequest request) {
    return request.getHeader(Constants.HEADER_AUTHORIZACION_KEY);
  }

  private ErrorJsonResponseBody buildEmailNotExistsResponseBody() {
    return ErrorJsonResponseBody.buildUnprocessableEntityJsonBody(ErrorResponseMsg.EMAIL_NOT_EXISTS);
  }

  private ErrorJsonResponseBody buildWrongSubscriberAccountResponseBody() {
    return ErrorJsonResponseBody.buildLockedJsonBody(ErrorResponseMsg.WRONG_SUBSCRIBER_ACCOUNT);
  }
}
