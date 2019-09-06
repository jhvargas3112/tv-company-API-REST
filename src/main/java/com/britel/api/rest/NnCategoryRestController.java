package com.britel.api.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.britel.api.model.NnCategory;
import com.britel.api.service.NnCategoryService;

@RestController
public class NnCategoryRestController {
  @Autowired
  private NnCategoryService nnCategoryService;

  /**
   * Gets all the channel categories with the information of each channel of the these.
   * 
   * @return
   */
  @GetMapping(value = "/api/nn_category")
  public ResponseEntity<List<NnCategory>> getAllCategories() {
    return ResponseEntity.ok(nnCategoryService.findAll());
  }

  /**
   * Gets the information of a specific channel category with the information of the channels of this. 
   * 
   * @param nNCategoryId
   * @return
   */
  @GetMapping(value = "/api/nn_category/by_id/{nncategory_id}")
  public ResponseEntity<NnCategory> getCategoryById(@PathVariable(value = "nncategory_id", required = true) Integer nNCategoryId) {
    NnCategory nnCategory = nnCategoryService.findById(nNCategoryId);

    if (nnCategory == null) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.ok(nnCategory);
    }
  }
}
