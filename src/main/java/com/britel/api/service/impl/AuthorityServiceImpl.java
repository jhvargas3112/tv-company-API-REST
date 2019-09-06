package com.britel.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.britel.api.model.Authority;
import com.britel.api.repository.AuthorityRepository;
import com.britel.api.service.AuthorityService;

@Service
public class AuthorityServiceImpl implements AuthorityService {
  @Autowired
  private AuthorityRepository authorityRepository;

  @Override
  public List<Authority> findAll() {
    return authorityRepository.findAll();
  }

  @Override
  public List<Authority> findByEmail(String email) {
    return authorityRepository.findByEmail(email);
  }
}
