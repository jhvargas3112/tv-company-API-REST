package com.britel.api.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jhonny Vargas.
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "package")
public class Package implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "IdPackage")
  private Integer idPackage;
  @Column(name = "Description")
  private String description;
  @Column(name = "Name")
  private String name;
  @Column(name = "Public", columnDefinition = "BIT")
  private Boolean _public;
  @Column(name = "Price")
  private Integer price;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinTable(
      name = "containpackagechannel",
      joinColumns = @JoinColumn(name = "Package", referencedColumnName = "IdPackage"),
      inverseJoinColumns = @JoinColumn(name = "Channel", referencedColumnName = "IdChannel")
      )
  private List<Channel> channels = new ArrayList<Channel>();

  public Integer getIdPackage() {
    return idPackage;
  }

  public void setIdPackage(Integer idPackage) {
    this.idPackage = idPackage;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean get_public() {
    return _public;
  }

  public void set_public(Boolean _public) {
    this._public = _public;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  public List<Channel> getChannels() {
    return channels;
  }

  public void setChannels(List<Channel> channels) {
    this.channels = channels;
  }
}