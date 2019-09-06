package com.britel.api.model;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "video")
public class Video implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "IdVideo")
  private Integer idVideo;
  @Column(name = "Title")
  private String title;
  @Column(name = "PublishDate")
  private Date publishDate;
  @Column(name = "UnpublishDate")
  private Date unpublishDate;
  @Column(name = "Visibility", columnDefinition = "enum('Public','Private')")
  private String visibility;
  @Column(name = "Quality", columnDefinition = "enum('SD','HD')")
  private String quality;
  @Column(name = "Url")
  private String url;
  @Column(name = "Duration")
  private Integer duration;
  @Column(name = "ImageUrl")
  private String imageUrl;

  public Integer getIdVideo() {
    return idVideo;
  }

  public void setIdVideo(Integer idVideo) {
    this.idVideo = idVideo;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Date getPublishDate() {
    return publishDate;
  }

  public void setPublishDate(Date publishDate) {
    this.publishDate = publishDate;
  }

  public Date getUnpublishDate() {
    return unpublishDate;
  }

  public void setUnpublishDate(Date unpublishDate) {
    this.unpublishDate = unpublishDate;
  }

  public String getVisibility() {
    return visibility;
  }

  public void setVisibility(String visibility) {
    this.visibility = visibility;
  }

  public String getQuality() {
    return quality;
  }

  public void setQuality(String quality) {
    this.quality = quality;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Integer getDuration() {
    return duration;
  }

  public void setDuration(Integer duration) {
    this.duration = duration;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }
}