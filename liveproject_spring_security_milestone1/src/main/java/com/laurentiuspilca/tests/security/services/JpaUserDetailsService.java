package com.laurentiuspilca.tests.security.services;

import com.laurentiuspilca.tests.entities.User;
import com.laurentiuspilca.tests.security.model.UserDetailsSecurityWrapper;
import com.laurentiuspilca.tests.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public JpaUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findUserByUsername(username);
    return user
            .map(UserDetailsSecurityWrapper::new)
            .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
  }
}
