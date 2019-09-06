package com.britel.api.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.time.DateUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Jhonny Vargas.
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "user")
public class User implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "IdUser")
  @JsonIgnore
  private Integer idUser;
  @Column(name = "Type", columnDefinition="enum('Admin','Company','Subscriber','VOD')")
  @JsonIgnore
  private String type;
  @Column(name = "SubscriberType", columnDefinition="enum('STB','MOVIL','ANDROIDTV','SAMSUNG', 'LG')")
  @JsonIgnore
  private String subscriberType;
  @Column(name = "Email")
  private String email;
  @Column(name = "Description")
  @JsonIgnore
  private String description;
  @Column(name = "Password")
  @JsonIgnore
  private String password;
  @Column(name = "UserJwt")
  @JsonIgnore
  private String userJwt;
  @Column(name = "UserToken")
  @JsonIgnore
  private String userToken;
  @Column(name = "City")
  private String city;
  @Column(name = "Country")
  private String country;
  @Column(name = "Address")
  private String address;
  @Column(name = "PostalCode")
  private Integer postalCode;
  @Column(name = "FirstName")
  private String firstName;
  @Column(name = "LastName")
  private String lastName;
  @Column(name = "Active", columnDefinition = "BIT")
  private Boolean active;
  @Column(name = "Test", columnDefinition = "BIT")
  @JsonIgnore
  private Boolean test;
  @Column(name = "DeactivationRequest", columnDefinition = "BIT")
  private Boolean deactivationRequest;
  @Column(name = "LastRenewal", columnDefinition="TIMESTAMP")
  private Timestamp lastRenewal;
  @Column(name = "NextRenewal", columnDefinition="TIMESTAMP")
  private Timestamp nextRenewal;
  @Column(name = "IdCompany")
  @JsonIgnore
  private Integer idCompany;

  /* @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "email")
  private Authority authority; */

  public User() {}

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable(
      name = "devicesubscriber",
      joinColumns = @JoinColumn(name = "Subscriber", referencedColumnName = "IdUser"),
      inverseJoinColumns = @JoinColumn(name = "Device", referencedColumnName = "Udid")
      )
  @JsonIgnore
  private List<Device> devices = new ArrayList<Device>();

  public User(Integer idUser, String type, String subscriberType, String email, String description,
      String password,  String userJwt, String userToken, String city, String country,
      String address, Integer postalCode, String firstName,
      String lastName, Boolean active, Boolean test,
      Boolean deactivationRequest, Timestamp lastRenewal,
      Timestamp nextRenewal,
      Integer idCompany) {
    this.idUser = idUser;
    this.type = type;
    this.subscriberType = subscriberType;
    this.email = email;
    this.description = description;
    this.password = password;
    this.userJwt = userJwt;
    this.userToken = userToken;
    this.city = city;
    this.country = country;
    this.address = address;
    this.postalCode = postalCode;
    this.firstName = firstName;
    this.lastName = lastName;
    this.active = active;
    this.test = test;
    this.deactivationRequest = deactivationRequest;
    this.lastRenewal = lastRenewal;
    this.nextRenewal = nextRenewal;
    this.idCompany = idCompany;
  }

  public static User buildSubscriberForBilling(String email, String password,
      String subscriberType, User company, String city, String country, String address,
      Integer postalCode, String firstName, String lastName) throws NullPointerException {
    User subscriber = buildSubscriber(email, password, subscriberType, company, city, country, address, postalCode, firstName, lastName);
    subscriber.setActive(true);
    subscriber.setTest(false);

    return subscriber;
  }

  public static User buildSubscriberForTesting(String email, String password, 
      String subscriberType, User company, String city, String country, String address,
      Integer postalCode, String firstName, String lastName) throws NullPointerException {
    User subscriber = buildSubscriber(email, password, subscriberType, company, city, country, address, postalCode, firstName, lastName);
    subscriber.setActive(true);
    subscriber.setTest(true);

    return subscriber;
  }

  /**
   * Constructs a new subscriber non active by default.
   * 
   * @param email
   * @param password
   * @param company
   * @param city
   * @param country
   * @param address
   * @param postalCode
   * @param firstName
   * @param lastName
   * @return
   * @throws NullPointerException
   */
  private static User buildSubscriber(String email, String password,
      String subscriberType, User company, String city, String country, String address,
      Integer postalCode, String firstName, String lastName) throws NullPointerException {
    if (company == null)
      throw new NullPointerException();

    Timestamp lastRenewal = new Timestamp(new Date().getTime());
    Timestamp nextRenewal = new Timestamp(DateUtils.addDays(lastRenewal, 30).getTime());

    return new User(null, "Subscriber", subscriberType, email, "Suscriptor de" + company.getDescription(),
        password, null, null, city, country, address, postalCode, firstName, lastName,
        false, false, false, lastRenewal, nextRenewal, company.getIdUser());
  }

  public Integer getIdUser() {
    return idUser;
  }

  public void setIdUser(Integer idUser) {
    this.idUser = idUser;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUserJwt() {
    return userJwt;
  }

  public void setUserJwt(String userJwt) {
    this.userJwt = userJwt;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Integer getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(Integer postalCode) {
    this.postalCode = postalCode;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public Boolean getTest() {
    return test;
  }

  public void setTest(Boolean test) {
    this.test = test;
  }

  public Boolean getDeactivationRequest() {
    return deactivationRequest;
  }

  public void setDeactivationRequest(Boolean deactivationRequest) {
    this.deactivationRequest = deactivationRequest;
  }

  public Timestamp getLastRenewal() {
    return lastRenewal;
  }

  public void setLastRenewal(Timestamp lastRenewal) {
    this.lastRenewal = lastRenewal;
  }

  public Timestamp getNextRenewal() {
    return nextRenewal;
  }

  public void setNextRenewal(Timestamp nextRenewal) {
    this.nextRenewal = nextRenewal;
  }

  public Integer getIdCompany() {
    return idCompany;
  }

  public void setIdCompany(Integer idCompany) {
    this.idCompany = idCompany;
  }

  public List<Device> getDevices() {
    return devices;
  }

  public void setDevices(List<Device> devices) {
    this.devices = devices;
  }
}
