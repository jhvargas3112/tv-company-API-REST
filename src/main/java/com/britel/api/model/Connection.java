package com.britel.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Jhonny Vargas.
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "connection")
public class Connection implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "conextionId")
  private Integer connectionId;
  @Column(name = "numConnection")
  private Integer numConnection;
  @Column(name = "description")
  private String description;
  @Column(name = "active", columnDefinition="BIT")
  private Boolean active;
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "IdOrganization")
  private Organization organization;

  public Integer getConnectionId() {
    return connectionId;
  }

  public void setConnectionId(Integer connectionId) {
    this.connectionId = connectionId;
  }

  public Integer getNumConnection() {
    return numConnection;
  }

  public void setNumConnection(Integer numConnection) {
    this.numConnection = numConnection;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public Organization getOrganization() {
    return organization;
  }

  public void setOrganization(Organization organization) {
    this.organization = organization;
  }
}