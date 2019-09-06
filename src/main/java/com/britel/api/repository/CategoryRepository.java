package com.britel.api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.britel.api.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
  
  List<Category> findByIdCategoryFather(Integer IdCategoryFather);

  List<Category> findByIdCategory(Integer IdCategory);

}
