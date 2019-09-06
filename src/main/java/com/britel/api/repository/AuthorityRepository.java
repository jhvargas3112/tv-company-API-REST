package com.britel.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.britel.api.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
  List<Authority> findByEmail(String email);
}
