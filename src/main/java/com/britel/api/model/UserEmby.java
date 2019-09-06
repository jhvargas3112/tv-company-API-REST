package com.britel.api.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

/**
 * @author Jhonny Vargas.
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "useremby")
public class UserEmby implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "userId")
  private Integer userId;
  @Column(name = "firstName")
  private String firstName;
  @Column(name = "lastName")
  private String lastName;
  @Column(name = "phone")
  private String phone;
  @Column(name = "email")
  private String email;
  @Column(name = "password")
  private String password;
  @Column(name = "creationDate")
  private Date creationDate;
  @Column(name = "expireDate")
  private Date expireDate;
  @Column(name = "accessedDate")
  private Date accessedDate;
  @Column(name = "numConnection")
  private Integer numConnection;
  @Column(name = "active", columnDefinition = "BIT")
  private Boolean active;

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Date getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }

  public Date getExpireDate() {
    return expireDate;
  }

  public void setExpireDate(Date expireDate) {
    this.expireDate = expireDate;
  }

  public Date getAccessedDate() {
    return accessedDate;
  }

  public void setAccessedDate(Date accessedDate) {
    this.accessedDate = accessedDate;
  }

  public Integer getNumConnection() {
    return numConnection;
  }

  public void setNumConnection(Integer numConnection) {
    this.numConnection = numConnection;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }
}
