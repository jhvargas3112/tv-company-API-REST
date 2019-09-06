package com.britel.api.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Jhonny Vargas.
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "zapping")
public class Zapping implements Serializable {
  @EmbeddedId
  private ZappingId zappingId;
  @Column(name = "udid", insertable = false, updatable= false)
  private String udid;
  @Column(name = "idChannel", insertable = false, updatable= false)
  private Integer idChannel;
  @Column(name = "changeChannelTimeStamp", columnDefinition="TIMESTAMP", insertable = false, updatable= false)
  private Timestamp changeChannelTimeStamp;

  public ZappingId getZappingId() {
    return zappingId;
  }

  public void setZappingId(ZappingId zappingId) {
    this.zappingId = zappingId;
  }

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
}
