package com.britel.api.service;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.commons.mail.EmailException;

import com.britel.api.model.User;
import com.britel.api.service.exception.IsNotSubscriberEmailException;
import com.britel.api.service.exception.IsNotSubscriberOfTheCompanyException;

/**
 * @author Jhonny Vargas.
 */

public interface SubscriberAccountService {
  public void saveSubscriber(User subscriber, ArrayList<Integer> organizations);

  public User findByEmail(String email);
  public User findByIdCompanyAndEmail(Integer companyId, String email);
  public User findByIdCompanyAndIdUser(Integer companyId, Integer idUser);

  public Boolean exists(String email);

  public Boolean isSubscriberOfTheCompany(Integer companyId, String email);

  public void sendPasswordRecoveryCode(String email) throws EmailException, IsNotSubscriberEmailException;
  public Boolean changePassword(Integer recoveryCode, String newPassword);

  public Timestamp activateSubscriber(Integer companyId, String email) throws IsNotSubscriberOfTheCompanyException;
  public void subscriberDeactivateRequest(Integer companyId, String email) throws IsNotSubscriberOfTheCompanyException;
  public Timestamp getNextRenewal(Integer companyId, String email) throws IsNotSubscriberOfTheCompanyException;
}
