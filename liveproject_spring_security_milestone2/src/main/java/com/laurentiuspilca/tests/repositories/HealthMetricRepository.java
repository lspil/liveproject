package com.laurentiuspilca.tests.repositories;

import com.laurentiuspilca.tests.entities.HealthMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HealthMetricRepository extends JpaRepository<HealthMetric, Integer> {

  @Query("SELECT h FROM HealthMetric h WHERE h.profile.username=?#{authentication.name}")
  List<HealthMetric> findHealthMetricHistory();

  @Query("DELETE FROM HealthMetric h WHERE h.profile.username=:username")
  void deleteAllForUser(String username);
}
