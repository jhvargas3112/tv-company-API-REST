package com.britel.api.service;

import com.britel.api.model.User;

/**
 * @author Jhonny Vargas.
 */

public interface UserService {
  public Boolean exists(String email);

  public User findByEmail(String email);
  public User findByEmailAndType(String email, String type);
}
