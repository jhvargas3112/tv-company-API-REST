package com.britel.api.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Jhonny Vargas.
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "device")
public class Device implements Serializable {
  @Id
  @Column(name = "Udid")
  private String udid;
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "Company")
  private User company;
  @Column(name = "Active", columnDefinition="BIT")
  private Boolean active;
  @Column(name = "ActiveDate")
  private Timestamp activeDate;
  @Column(name = "creationDate", columnDefinition="TIMESTAMP")
  private Timestamp creationDate;
  @Column(name = "Test", columnDefinition="BIT")
  private Boolean test;
  @Column(name = "DesactivationDate", columnDefinition="TIMESTAMP")
  private Timestamp desactivationDate;
  @Column(name = "LastAccessDate", columnDefinition="TIMESTAMP")
  private Timestamp lastAccessDate;
  @Column(name = "Description")   
  private String description;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "zappingId.udid")  
  private Set<Zapping> zappings;

  public String getUdid() {
    return udid;
  }

  public void setUdid(String udid) {
    this.udid = udid;
  }

  public User getCompany() {
    return company;
  }

  public void setCompany(User company) {
    this.company = company;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public Timestamp getActiveDate() {
    return activeDate;
  }

  public void setActiveDate(Timestamp activeDate) {
    this.activeDate = activeDate;
  }

  public Timestamp getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Timestamp creationDate) {
    this.creationDate = creationDate;
  }

  public Boolean getTest() {
    return test;
  }

  public void setTest(Boolean test) {
    this.test = test;
  }

  public Timestamp getDesactivationDate() {
    return desactivationDate;
  }

  public void setDesactivationDate(Timestamp desactivationDate) {
    this.desactivationDate = desactivationDate;
  }

  public Timestamp getLastAccessDate() {
    return lastAccessDate;
  }

  public void setLastAccessDate(Timestamp lasAccessDate) {
    this.lastAccessDate = lasAccessDate;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Set<Zapping> getZappings() {
    return zappings;
  }

  public void setZappings(Set<Zapping> zappings) {
    this.zappings = zappings;
  }
}
