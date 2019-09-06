package com.britel.api.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * @author Jhonny Vargas.
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "nncategory")
public class NnCategory implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "nncategory_id")
  private int nNCategoryId;
  @Column(name = "name")
  private String name;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinTable(
      name = "channel_nncategory",
      joinColumns = @JoinColumn(name = "category", referencedColumnName = "nncategory_id"),
      inverseJoinColumns = @JoinColumn(name = "channel", referencedColumnName = "IdChannel")
      )
  @OrderBy("IdChannel ASC")
  private Set<Channel> channels;

  public int getnNCategoryId() {
    return nNCategoryId;
  }

  public void setnNCategoryId(int nNCategoryId) {
    this.nNCategoryId = nNCategoryId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Channel> getChannels() {
    return channels;
  }

  public void setChannels(Set<Channel> channels) {
    this.channels = channels;
  }
}
