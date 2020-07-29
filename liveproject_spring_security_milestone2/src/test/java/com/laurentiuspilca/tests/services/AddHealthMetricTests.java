package com.laurentiuspilca.tests.services;

import com.laurentiuspilca.tests.exceptions.NonExistentHealthProfileException;
import com.laurentiuspilca.tests.repositories.HealthProfileRepository;
import com.laurentiuspilca.tests.services.context.TestUser;
import com.laurentiuspilca.tests.entities.HealthMetric;
import com.laurentiuspilca.tests.entities.HealthProfile;
import com.laurentiuspilca.tests.repositories.HealthMetricRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class AddHealthMetricTests {

  @Autowired
  private HealthMetricService healthMetricService;

  @MockBean
  private HealthMetricRepository healthMetricRepository;

  @MockBean
  private HealthProfileRepository healthProfileRepository;

  @Test
  @TestUser(username = "john")
  @DisplayName("Considering a request is done to add a new metric record for the authenticated user," +
          " and the user profile exists " +
          " assert that the record is added to the database.")
  void addHealthMetricValidUserAuthenticatedTest() {
    HealthProfile healthProfile = new HealthProfile();
    healthProfile.setUsername("john");

    HealthMetric healthMetric = new HealthMetric();
    healthMetric.setProfile(healthProfile);

    when(healthProfileRepository.findHealthProfileByUsername(healthProfile.getUsername()))
            .thenReturn(Optional.of(healthProfile));

    healthMetricService.addHealthMetric(healthMetric);

    verify(healthMetricRepository).save(healthMetric);
  }

  @Test
  @TestUser(username = "john")
  @DisplayName("Considering a request is done to add a new metric record for the authenticated user," +
          " and the user profile deesn't exist assert that the record is not added to the database " +
          " and the app throws an exception.")
  void addHealthMetricValidUserAuthenticatedProfileDoesntExistTest() {
    HealthProfile healthProfile = new HealthProfile();
    healthProfile.setUsername("john");

    HealthMetric healthMetric = new HealthMetric();
    healthMetric.setProfile(healthProfile);

    when(healthProfileRepository.findHealthProfileByUsername(healthProfile.getUsername()))
            .thenReturn(Optional.empty());

    assertThrows(NonExistentHealthProfileException.class,
            () -> healthMetricService.addHealthMetric(healthMetric));

    verify(healthMetricRepository, never()).save(healthMetric);
  }

  @Test
  @TestUser(username = "john")
  @DisplayName("Considering a request is done to add a new record for another user than " +
          " the authenticated user, assert that the record is not added to the database " +
          "and the app throws an exception.")
  void addHealthMetricDifferentUserAuthenticatedTest() {
    HealthProfile healthProfile = new HealthProfile();
    healthProfile.setUsername("bill");

    HealthMetric healthMetric = new HealthMetric();
    healthMetric.setProfile(healthProfile);

    assertThrows(AccessDeniedException.class,
            () -> healthMetricService.addHealthMetric(healthMetric));

    verify(healthMetricRepository, never()).save(healthMetric);
  }

}
