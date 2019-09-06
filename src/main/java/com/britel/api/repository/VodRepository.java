package com.britel.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.britel.api.model.Vod;

public interface VodRepository extends JpaRepository<Vod, Integer> {
  Vod findByOrganization(Integer integer);
}
