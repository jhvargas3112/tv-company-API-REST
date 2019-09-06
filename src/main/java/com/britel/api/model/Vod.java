package com.britel.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Jhonny Vargas.
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "vod")
public class Vod implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "IdVod")
  private Integer idVod;
  @Column(name = "Organization")
  private Integer organization;
  @Column(name = "Category")
  private Integer category;

  public Integer getIdVod() {
    return idVod;
  }

  public void setIdVod(Integer idVod) {
    this.idVod = idVod;
  }

  public Integer getOrganization() {
    return organization;
  }

  public void setOrganization(Integer organization) {
    this.organization = organization;
  }

  public Integer getCategory() {
    return category;
  }

  public void setCategory(Integer category) {
    this.category = category;
  } 
}