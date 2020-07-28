package com.laurentiuspilca.tests.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Value("${publicKey}")
  private String publicKey;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.oauth2ResourceServer(
            c -> c.jwt(
                    j -> j.decoder(jwtDecoder())
            )
    );
    http.authorizeRequests()
            .mvcMatchers(HttpMethod.DELETE, "/**").hasAuthority("admin")
            .anyRequest().authenticated();
  }

  @Bean
  public JwtDecoder jwtDecoder() {
    try {
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      var key = Base64.getDecoder().decode(publicKey);

      var x509 = new X509EncodedKeySpec(key);
      var rsaKey = (RSAPublicKey) keyFactory.generatePublic(x509);
      return NimbusJwtDecoder.withPublicKey(rsaKey).build();
    } catch (Exception e) {
      throw new RuntimeException("Wrong public key");
    }
  }

  @Bean
  public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
    return new SecurityEvaluationContextExtension();
  }
}