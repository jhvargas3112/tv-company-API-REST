package com.britel.api.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.britel.api.model.NnCategory;
import com.britel.api.repository.NnCategoryRepository;
import com.britel.api.service.NnCategoryService;

@Service
public class NnCategoryServiceImpl implements NnCategoryService {
  @Autowired
  private NnCategoryRepository nnCategoryRepository;

  @Override
  public List<NnCategory> findAll() {
    return nnCategoryRepository.findAll();
  }

  @Override
  public NnCategory findById(Integer nNCategoryId) {
    return nnCategoryRepository.findById(nNCategoryId).isPresent() ? nnCategoryRepository.findById(nNCategoryId).get() : null;
  }
}
