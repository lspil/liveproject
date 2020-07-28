package com.laurentiuspilca.tests.services;

import com.laurentiuspilca.tests.entities.HealthProfile;
import com.laurentiuspilca.tests.exceptions.NonExistentHealthProfileException;
import com.laurentiuspilca.tests.repositories.HealthProfileRepository;
import com.laurentiuspilca.tests.services.context.TestUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FindHealthProfileTests {

  @Autowired
  private HealthProfileService healthProfileService;

  @MockBean
  private HealthProfileRepository healthProfileRepository;

  @Test
  @TestUser(username = "bill", authorities = "read")
  @DisplayName("Considering the call is done for another user than the authenticated one, " +
          "assert that calling the method throws an exception.")
  public void findHealthProfileWrongUserTests() {
    HealthProfile healthProfile = new HealthProfile();
    healthProfile.setUsername("john");

    when(healthProfileRepository.findHealthProfileByUsername("john"))
      .thenReturn(Optional.of(healthProfile));

    assertThrows(AccessDeniedException.class,
            () -> healthProfileService.findHealthProfile("john"));
  }

  @Test
  @TestUser(username = "john", authorities = "read")
  @DisplayName("Considering the call is done for the authenticated user but " +
          " no record exists for that user in the database, " +
          " assert that calling the method throws an exception.")
  public void findHealthProfileProfileDoesntExistTests() {
    when(healthProfileRepository.findHealthProfileByUsername("john"))
            .thenReturn(Optional.empty());

    assertThrows(NonExistentHealthProfileException.class,
            () -> healthProfileService.findHealthProfile("john"));
  }

  @Test
  @TestUser(username = "john", authorities = "read")
  @DisplayName("Considering the call is done for the authenticated user and " +
          " a record exists for that user in the database, " +
          " assert that calling the method returns the valid record.")
  public void findHealthProfileProfileExistsTests() {
    HealthProfile healthProfile = new HealthProfile();
    healthProfile.setUsername("john");

    when(healthProfileRepository.findHealthProfileByUsername("john"))
            .thenReturn(Optional.of(healthProfile));

    HealthProfile result = healthProfileService.findHealthProfile("john");

    assertEquals(healthProfile, result);
  }

  @Test
  @TestUser(username = "bill", authorities = "admin")
  @DisplayName("Considering the call is done by and admin for a user and " +
          " a record exists for that user in the database, " +
          " assert that calling the method returns the valid record.")
  public void findHealthProfileProfileAdminTests() {
    HealthProfile healthProfile = new HealthProfile();
    healthProfile.setUsername("john");

    when(healthProfileRepository.findHealthProfileByUsername("john"))
            .thenReturn(Optional.of(healthProfile));

    HealthProfile result = healthProfileService.findHealthProfile("john");

    assertEquals(healthProfile, result);
  }
}
