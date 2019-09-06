package com.britel.api.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Jhonny Vargas.
 */

@SuppressWarnings("serial")
@Embeddable
public class ZappingId implements Serializable {
  @Column(name = "udid")
  private String udid;
  @Column(name = "idChannel")
  private Integer idChannel;
  @Column(name = "changeChannelTimeStamp", columnDefinition="TIMESTAMP")
  private Timestamp changeChannelTimeStamp;

  public String getUdid() {
    return udid;
  }

  public void setUdid(String udid) {
    this.udid = udid;
  }

  public Integer getIdChannel() {
    return idChannel;
  }

  public void setIdChannel(Integer idChannel) {
    this.idChannel = idChannel;
  }

  public Timestamp getChangeChannelTimeStamp() {
    return changeChannelTimeStamp;
  }

  public void setChangeChannelTimeStamp(Timestamp changeChannelTimeStamp) {
    this.changeChannelTimeStamp = changeChannelTimeStamp;
  }

  @Override
  public String toString() {
    return "";
  }
}