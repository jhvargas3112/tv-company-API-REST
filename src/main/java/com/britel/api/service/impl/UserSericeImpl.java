package com.britel.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.britel.api.model.User;
import com.britel.api.repository.UserRepository;
import com.britel.api.service.UserService;

/**
 * @author Jhonny Vargas.
 */

@Service
public class UserSericeImpl implements UserService {
  @Override
  public Boolean exists(String email) {
    return findByEmail(email) != null;
  }

  @Autowired
  private UserRepository userRepository;

  @Override
  public User findByEmail(String email) {
    return userRepository.findByEmail(email).isPresent() ? userRepository.findByEmail(email).get() : null;
  }

  @Override
  public User findByEmailAndType(String email, String type) {
    return userRepository.findByEmailAndType(email, type).isPresent() ? userRepository.findByEmailAndType(email, type).get() : null;
  }
}
