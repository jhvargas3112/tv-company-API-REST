package com.britel.api.service;

import java.util.List;
import java.util.Optional;
import com.britel.api.model.Organization;

/**
 * @author Jhonny Vargas.
 */

public interface OrganizationService {
  public Optional<Organization> findById(Integer id);
  public void save(Organization organization);
  public void addUserOrganization(Integer idUser, Integer organization);
  public List<Organization> findOrganizations();
}
