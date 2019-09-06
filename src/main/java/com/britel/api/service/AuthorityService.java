package com.britel.api.service;

import java.util.List;

import com.britel.api.model.Authority;

public interface AuthorityService {
  public List<Authority> findAll();
  public List<Authority> findByEmail(String email);
}
