package com.laurentiuspilca.liveproject_spring_security_milestone1.security.model;

import com.laurentiuspilca.liveproject_spring_security_milestone1.entities.Authority;
import org.springframework.security.core.GrantedAuthority;

public class GrantedAuthoritySecurityWrapper
        implements GrantedAuthority {

  private final Authority authority;

  public GrantedAuthoritySecurityWrapper(Authority authority) {
    this.authority = authority;
  }

  @Override
  public String getAuthority() {
    return authority.getName();
  }
}
