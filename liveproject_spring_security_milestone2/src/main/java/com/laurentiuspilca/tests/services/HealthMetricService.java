package com.laurentiuspilca.tests.services;

import com.laurentiuspilca.tests.entities.HealthMetric;
import com.laurentiuspilca.tests.entities.HealthProfile;
import com.laurentiuspilca.tests.exceptions.NonExistentHealthProfileException;
import com.laurentiuspilca.tests.repositories.HealthMetricRepository;
import com.laurentiuspilca.tests.repositories.HealthProfileRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HealthMetricService {

  private final HealthMetricRepository healthMetricRepository;
  private final HealthProfileRepository healthProfileRepository;

  public HealthMetricService(HealthMetricRepository healthMetricRepository, HealthProfileRepository healthProfileRepository) {
    this.healthMetricRepository = healthMetricRepository;
    this.healthProfileRepository = healthProfileRepository;
  }

  @PreAuthorize("#healthMetric.profile.username == authentication.principal.claims['user_name']")
  public void addHealthMetric(HealthMetric healthMetric) {
    Optional<HealthProfile> profile = healthProfileRepository.findHealthProfileByUsername(healthMetric.getProfile().getUsername());

    profile.ifPresentOrElse(
            p ->
            {
              healthMetric.setProfile(p);
              healthMetricRepository.save(healthMetric);
            },
            () -> {
              throw new NonExistentHealthProfileException("The profile doesn't exist");
            });

    ;
  }

  public List<HealthMetric> findHealthMetricHistory() {
    return healthMetricRepository.findHealthMetricHistory();
  }

  @PreAuthorize("hasAuthority('admin')")
  public void deleteHealthMetricForUser(String username) {
    Optional<HealthProfile> profile = healthProfileRepository.findHealthProfileByUsername(username);

    profile.ifPresentOrElse(healthMetricRepository::deleteAllForUser,
            () -> {
              throw new NonExistentHealthProfileException("The profile doesn't exist");
            }
    );
  }
}
