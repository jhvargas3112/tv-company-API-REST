package com.britel.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.britel.api.model.Organization;

/**
 * @author Jhonny Vargas.
 */

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
  @Modifying
  @Query(value = "INSERT INTO companyorganization (Company, Organization) VALUES (?1, ?2)", nativeQuery = true)
  void addUserOrganization(Integer idUser, Integer organization);
}
