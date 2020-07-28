package com.laurentiuspilca.tests.repositories;

import com.laurentiuspilca.tests.entities.HealthProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HealthProfileRepository extends JpaRepository<HealthProfile, Integer> {

  Optional<HealthProfile> findHealthProfileByUsername(String username);
}
