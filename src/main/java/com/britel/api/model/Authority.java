package com.britel.api.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "authorities")
public class Authority implements Serializable {
  @Id
  @Column(name = "email")
  private String email;
  @Column(name = "authority")
  private String authority;

  /* @OneToMany(mappedBy = "authority")
  private Set<User> authorityUsers; */

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAuthority() {
    return authority;
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }
}
