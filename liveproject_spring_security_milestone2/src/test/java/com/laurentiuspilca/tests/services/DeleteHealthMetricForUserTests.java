package com.laurentiuspilca.tests.services;

import com.laurentiuspilca.tests.repositories.HealthMetricRepository;
import com.laurentiuspilca.tests.services.context.TestUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class DeleteHealthMetricForUserTests {

  @Autowired
  private HealthMetricService healthMetricService;

  @MockBean
  private HealthMetricRepository healthMetricRepository;

  @Test
  @TestUser(username = "john", authorities = "admin")
  @DisplayName("Considering a request is done by an admin user to remove a record " +
          " assert that the record is removed from the database.")
  void deleteHealthMetricForUserWithAdminTest() {
    healthMetricService.deleteHealthMetricForUser("bill");

    verify(healthMetricRepository).deleteAllForUser("bill");
  }

  @Test
  @TestUser(username = "john", authorities = "read")
  @DisplayName("Considering a request is done by a non-admin user to remove a record" +
          " assert that the record is not removed from the database and" +
          " the application throws an exception.")
  void deleteHealthMetricForUserWithNonAdminTest() {
    assertThrows(AccessDeniedException.class,
            () ->  healthMetricService.deleteHealthMetricForUser("bill"));

    verify(healthMetricRepository, never()).deleteAllForUser("bill");
  }
}
