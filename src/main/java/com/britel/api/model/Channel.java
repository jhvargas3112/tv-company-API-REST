package com.britel.api.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Jhonny Vargas.
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "channel")
public class Channel implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "IdChannel")
  private Integer idChannel;
  @Column(name = "LogicalChannelNumber")
  private Integer logicalChannelNumber;
  @Column(name = "Url")
  private String url;
  @Column(name = "Name")
  private String name;
  @Column(name = "epg_name")
  private String epgName;
  @Column(name = "AspectRatio", columnDefinition = "enum('4:3','16:9')")
  private String aspectRatio;
  @Column(name = "Quality", columnDefinition = "enum('SD','HD')")
  private String quality;
  @Column(name = "Visibility", columnDefinition = "enum('Private','Public')")
  private String visibility;
  @Column(name = "media_player", columnDefinition = "enum('MEDIAPLAYER', 'EXOPLAYER', 'VLC')")
  private String mediaPlayer;
  @Column(name = "Logo")
  private String logo;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "zappingId.idChannel")  
  private Set<Zapping> deviceChannelsZapping;

  public Integer getIdChannel() {
    return idChannel;
  }

  public void setIdChannel(Integer idChannel) {
    this.idChannel = idChannel;
  }

  public Integer getLogicalChannelNumber() {
    return logicalChannelNumber;
  }

  public void setLogicalChannelNumber(Integer logicalChannelNumber) {
    this.logicalChannelNumber = logicalChannelNumber;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEpgName() {
    return epgName;
  }

  public void setEpgName(String epgName) {
    this.epgName = epgName;
  }

  public String getAspectRatio() {
    return aspectRatio;
  }

  public void setAspectRatio(String aspectRatio) {
    this.aspectRatio = aspectRatio;
  }

  public String getQuality() {
    return quality;
  }

  public void setQuality(String quality) {
    this.quality = quality;
  }

  public String getVisibility() {
    return visibility;
  }

  public void setVisibility(String visibility) {
    this.visibility = visibility;
  }

  public String getMediaPlayer() {
    return mediaPlayer;
  }

  public void setMediaPlayer(String mediaPlayer) {
    this.mediaPlayer = mediaPlayer;
  }

  public String getLogo() {
    return logo;
  }

  public void setLogo(String logo) {
    this.logo = logo;
  }
}
