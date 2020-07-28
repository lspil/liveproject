package com.laurentiuspilca.tests.security.model;

import com.laurentiuspilca.tests.entities.Authority;
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
