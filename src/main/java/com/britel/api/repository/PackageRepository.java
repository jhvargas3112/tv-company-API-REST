package com.britel.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.britel.api.model.Package;

/**
 * @author Jhonny Vargas.
 */

public interface PackageRepository extends JpaRepository<Package, Integer> {
  List<Package> findByIdPackage(Integer idPackage);
}