package com.britel.api.service;

import com.britel.api.model.User;

/**
 * @author Jhonny Vargas.
 */

public interface CompanyAccountService {
  public Boolean exists(String email);
  public User findByCompanyJwt(String userJwt);
  public void setCompanyJwt(String userJwt, String email);
}
