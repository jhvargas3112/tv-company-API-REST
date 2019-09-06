package com.britel.api.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.britel.api.model.User;
import com.britel.api.repository.UserRepository;
import com.britel.api.service.OrganizationService;
import com.britel.api.service.SubscriberAccountService;
import com.britel.api.service.UserService;
import com.britel.api.service.exception.IsNotSubscriberEmailException;
import com.britel.api.service.exception.IsNotSubscriberOfTheCompanyException;
import com.britel.api.service.utils.ExceptionMsg;

/**
 * @author Jhonny Vargas.
 */

@Service
public class SubscriberAccountServiceImpl implements SubscriberAccountService {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private OrganizationService organizationService;

  @Autowired
  private UserService userService;

  private static HashMap<Integer, String> recoveryPasswordRequests;

  @Override
  @Transactional
  public void saveSubscriber(User subscriber, ArrayList<Integer> organizations) {
    userRepository.save(subscriber);

    for (Integer organization : organizations) 
      organizationService.addUserOrganization(subscriber.getIdUser(), organization);
  }

  @Override
  public User findByEmail(String email) {
    return userService.findByEmailAndType(email, "Subscriber");
  }

  @Override
  public User findByIdCompanyAndEmail(Integer idCompany, String email) {
    return userRepository.findByIdCompanyAndEmail(idCompany, email).isPresent() ? userRepository.findByIdCompanyAndEmail(idCompany, email).get() : null;
  }

  @Override
  public User findByIdCompanyAndIdUser(Integer idCompany, Integer idUser) {
    return userRepository.findByIdCompanyAndIdUser(idCompany, idUser).isPresent() ? userRepository.findByIdCompanyAndIdUser(idCompany, idUser).get() : null;
  }

  @Override
  public Boolean exists(String email) {
    return findByEmail(email) != null;
  }

  @Override
  public Boolean isSubscriberOfTheCompany(Integer companyId, String email) {
    if (exists(email)) {
      User user = userService.findByEmailAndType(email, "Subscriber");
      return StringUtils.equals(user.getType(), "Subscriber") && user.getIdCompany().intValue() == companyId;
    } else {
      return false;
    }
  }

  public void sendPasswordRecoveryCode(String email) throws EmailException, IsNotSubscriberEmailException {
    if (!exists(email)) {
      throw new IsNotSubscriberEmailException(ExceptionMsg.IS_NOT_SUBSCRIBER_EMAIL);
    } else { 
      Email sendingEmail = new SimpleEmail();

      sendingEmail.setHostName("smtp.ionos.com");
      sendingEmail.setSmtpPort(465);
      sendingEmail.setAuthenticator(new DefaultAuthenticator("jhonny@xn--idaa.tv", "Spiritualized2018"));
      sendingEmail.setSSLOnConnect(true);
      sendingEmail.setFrom("jhonny@xn--idaa.tv");
      sendingEmail.setSubject("Recuperación de contraseña");
      int recoveryCode = RandomUtils.nextInt(100000, 999999);
      sendingEmail.setMsg("Hola, el código para restablecer tu contraseña es " + recoveryCode);
      sendingEmail.addTo(email);
      sendingEmail.send();

      updateRecoveryPasswordRequests(recoveryCode, email);
    }
  }

  @Override
  @Transactional
  public Boolean changePassword(Integer recoveryCode, String newPassword) {
    if (!recoveryPasswordRequests.containsKey(recoveryCode)) {
      return false;
    } else {
      userRepository.setPassword(newPassword, recoveryPasswordRequests.get(recoveryCode));
      recoveryPasswordRequests.remove(recoveryCode);

      return true;
    }
  }

  @Override
  @Transactional
  public Timestamp activateSubscriber(Integer companyId, String email) throws IsNotSubscriberOfTheCompanyException{
    if (!isSubscriberOfTheCompany(companyId, email)) {
      throw new IsNotSubscriberOfTheCompanyException(ExceptionMsg.WRONG_SUBSCRIBER);
    } else {
      userRepository.setActive(true, email);
      userRepository.setTest(false, email);
      userRepository.setDeactivationRequet(false, email);

      Timestamp lastRenewal = new Timestamp(new Date().getTime());
      Timestamp nextRenewal = new Timestamp(DateUtils.addDays(lastRenewal, 30).getTime());

      userRepository.setLastRenewal(lastRenewal, email);
      userRepository.setNextRenewal(nextRenewal, email);

      return nextRenewal;
    }
  }

  @Override
  @Transactional
  public void subscriberDeactivateRequest(Integer companyId, String email) throws IsNotSubscriberOfTheCompanyException {
    if (!isSubscriberOfTheCompany(companyId, email))
      throw new IsNotSubscriberOfTheCompanyException(ExceptionMsg.WRONG_SUBSCRIBER);
    else
      userRepository.setDeactivationRequet(true, email);
  }

  @Override
  public Timestamp getNextRenewal(Integer companyId, String email) throws IsNotSubscriberOfTheCompanyException {
    if (!isSubscriberOfTheCompany(companyId, email)) 
      throw new IsNotSubscriberOfTheCompanyException(ExceptionMsg.WRONG_SUBSCRIBER);
    else 
      return userService.findByEmailAndType(email, "Subscriber").getNextRenewal();
  }

  public static HashMap<Integer, String> updateRecoveryPasswordRequests(Integer recoveryCode, String email) {
    if (recoveryPasswordRequests == null) {
      recoveryPasswordRequests = new HashMap<Integer, String>();
      recoveryPasswordRequests.put(recoveryCode, email);
    } else {
      recoveryPasswordRequests.put(recoveryCode, email);
    }

    return recoveryPasswordRequests;
  }
}
