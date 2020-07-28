package com.laurentiuspilca.tests.services;

import com.laurentiuspilca.tests.entities.HealthMetric;
import com.laurentiuspilca.tests.repositories.HealthMetricRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthMetricService {

  private final HealthMetricRepository healthMetricRepository;

  public HealthMetricService(HealthMetricRepository healthMetricRepository) {
    this.healthMetricRepository = healthMetricRepository;
  }

  @PreAuthorize("#healthMetric.profile.username == authentication.principal.claims['user_name']")
  public void addHealthMetric(HealthMetric healthMetric) {
    healthMetricRepository.save(healthMetric);
  }

  public List<HealthMetric> findHealthMetricHistory() {
    return healthMetricRepository.findHealthMetricHistory();
  }

  @PreAuthorize("hasAuthority('admin')")
  public void deleteHealthMetricForUser(String username) {
    healthMetricRepository.deleteAllForUser(username);
  }
}
