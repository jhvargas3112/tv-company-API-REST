package com.britel.api.repository;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.britel.api.model.User;

/**
 * @author Jhonny Vargas.
 */

public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByEmail(String email);
  Optional<User> findByEmailAndType(String email, String type);
  Optional<User> findByUserJwt(String userJwt);
  Optional<User> findByIdCompanyAndEmail(Integer idCompany, String email);
  Optional<User> findByIdCompanyAndIdUser(Integer idCompany, Integer idUser);

  @Modifying
  @Query(value = "UPDATE user u SET u.Password = ?1 WHERE u.Email = ?2", nativeQuery = true)
  void setPassword(String password, String email);

  @Modifying
  @Query(value = "UPDATE user u SET u.UserJwt = ?1 WHERE u.Email = ?2", nativeQuery = true)
  void setUserJwt(String userJwt, String email);

  @Modifying
  @Query(value = "UPDATE User u SET u.Active = ?1 WHERE u.Email = ?2", nativeQuery = true)
  void setActive(Boolean active, String email);

  @Modifying
  @Query(value = "UPDATE User u SET u.Test = ?1 WHERE u.Email = ?2", nativeQuery = true)
  void setTest(Boolean test, String email);

  @Modifying
  @Query(value = "UPDATE User u SET u.DeactivationRequest = ?1 WHERE u.Email = ?2", nativeQuery = true)
  void setDeactivationRequet(Boolean deactivationRequest, String email);

  @Modifying
  @Query(value = "UPDATE User u SET u.LastRenewal = ?1 WHERE u.Email = ?2", nativeQuery = true)
  void setLastRenewal(Timestamp lastRenewal, String email);

  @Modifying
  @Query(value = "UPDATE User u SET u.NextRenewal = ?1 WHERE u.Email = ?2", nativeQuery = true)
  void setNextRenewal(Timestamp nextRenewal, String email);
}