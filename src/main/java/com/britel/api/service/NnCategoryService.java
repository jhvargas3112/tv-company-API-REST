package com.britel.api.service;

import java.util.List;

import com.britel.api.model.NnCategory;

public interface NnCategoryService {
  public List<NnCategory> findAll();
  public NnCategory findById(Integer nNCategoryId);
}
