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
@Table(name = "pin")
public class Pin implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "pinId")
  private Integer pinId;
  @Column(name = "code")
  private String code;
  @Column(name = "createDate")
  private Date createDate;
  @Column(name = "startDate")
  private Date startDate;
  @Column(name = "endDate")
  private Date endDate;
  private String duration;
  @Column(name = "typeDuration", columnDefinition="enum('DIAS','DIAS','SEMANA','SEMANAS','MES','MESES','AÑO','AÑOS')")
  private String typeDuration;
  @Column(name = "active", columnDefinition = "BIT")
  private Boolean active;
  @Column(name = "enable", columnDefinition = "BIT")
  private Boolean enable;

  public Pin() {}

  public Pin(Integer pinId, String code, Date createDate, Date startDate, Date endDate, String duration, String typeDuration, Boolean active, Boolean enable) {
    this.pinId = pinId;
    this.code = code;
    this.createDate = createDate;
    this.startDate = startDate;
    this.endDate = endDate;
    this.duration = duration;
    this.typeDuration = typeDuration;
    this.active = active;
    this.enable = enable;
  }

  public Integer getPinId() {
    return pinId;
  }

  public void setPinId(Integer pinId) {
    this.pinId = pinId;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public String getDuration() {
    return duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }

  public String getTypeDuration() {
    return typeDuration;
  }

  public void setTypeDuration(String typeDuration) {
    this.typeDuration = typeDuration;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public Boolean getEnable() {
    return enable;
  }

  public void setEnabe(Boolean enable) {
    this.enable = enable;
  }
}