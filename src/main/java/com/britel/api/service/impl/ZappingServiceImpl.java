package com.britel.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.britel.api.model.Zapping;
import com.britel.api.repository.ZappingRepository;
import com.britel.api.service.ZappingService;

/**
 * @author Jhonny Vargas.
 */

@Service
public class ZappingServiceImpl implements ZappingService {
  @Autowired
  private ZappingRepository zappingRepository;

  @Override
  public void save(Zapping zappingItem) {
    zappingRepository.save(zappingItem);
  }
}
