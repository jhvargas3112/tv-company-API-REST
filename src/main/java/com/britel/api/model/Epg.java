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
@Table(name = "epg")
public class Epg implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "IdEpg")
  private Integer idEpg;
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "Channel")
  private Channel channel;
  @Column(name = "PrimaryLanguage")
  private String primaryLanguage;
  @Column(name = "StartTime")
  private String startTime;
  @Column(name = "Duration")
  private Integer duration;
  @Column(name = "ShortDescription")
  private String shortDescription;
  @Column(name = "Name")
  private String name;
  @Column(name = "LongDescription")
  private String longDescription;

  public Integer getIdEpg() {
    return idEpg;
  }

  public void setIdEpg(Integer idEpg) {
    this.idEpg = idEpg;
  }

  public Channel getChannel() {
    return channel;
  }

  public void Channel(Channel channel) {
    this.channel = channel;
  }

  public String getPrimaryLanguage() {
    return primaryLanguage;
  }

  public void setPrimaryLanguage(String primaryLanguage) {
    this.primaryLanguage = primaryLanguage;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public Integer getDuration() {
    return duration;
  }

  public void setDuration(Integer duration) {
    this.duration = duration;
  }

  public String getShortDescription() {
    return shortDescription;
  }

  public void setShortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLongDescription() {
    return longDescription;
  }

  public void setLongDescription(String longDescription) {
    this.longDescription = longDescription;
  }
}
