package com.britel.api.model;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "app")
public class App implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "IdApp")
  private Integer idApp;
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "Organization")
  private Organization organization;
  @Column(name = "Name")
  private String name;
  @Column(name = "Status", columnDefinition="enum('ok','error')")
  private String status;
  @Column(name = "Version")
  private String version;
  @Column(name = "Url")
  private String url;
  @Column(name = "UploadDate")
  private Date uploadDate;

  public Integer getIdApp() {
    return idApp;
  }

  public void setIdApp(Integer idApp) {
    this.idApp = idApp;
  }

  public Organization getOrganization() {
    return organization;
  }

  public void setOrganization(Organization organization) {
    this.organization = organization;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Date getUploadDate() {
    return uploadDate;
  }

  public void setUploadDate(Date uploadDate) {
    this.uploadDate = uploadDate;
  }
}