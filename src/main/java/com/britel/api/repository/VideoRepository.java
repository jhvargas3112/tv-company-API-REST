package com.britel.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.britel.api.model.Video;

public interface VideoRepository extends JpaRepository<Video, Integer> {
  
}
