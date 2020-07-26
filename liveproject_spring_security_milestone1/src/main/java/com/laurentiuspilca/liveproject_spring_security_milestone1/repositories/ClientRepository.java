package com.laurentiuspilca.liveproject_spring_security_milestone1.repositories;

import com.laurentiuspilca.liveproject_spring_security_milestone1.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {

  Optional<Client> findClientByClientId(String clientId);
}
