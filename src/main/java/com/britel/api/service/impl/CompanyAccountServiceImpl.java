package com.britel.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.britel.api.model.User;
import com.britel.api.repository.UserRepository;
import com.britel.api.service.CompanyAccountService;
import com.britel.api.service.UserService;

/**
 * @author Jhonny Vargas.
 */

@Service
public class CompanyAccountServiceImpl implements CompanyAccountService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  @Override
  public Boolean exists(String email) {
    return userService.findByEmailAndType(email, "Company") != null;
  }

  @Override
  public User findByCompanyJwt(String userJwt) {
    return userRepository.findByUserJwt(userJwt).isPresent() ? userRepository.findByUserJwt(userJwt).get() : null;
  }

  /**
   * Adds the given JWT to the the company with the given email.
   */
  @Override
  @Transactional
  public void setCompanyJwt(String companyJwt, String email) {
    if (exists(email))
      userRepository.setUserJwt(companyJwt, email);
  }
}
