package PortalRegistraduriaBack.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import PortalRegistraduriaBack.enums.TipoRol;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  
  @Autowired
  private JWTAuthEntryPoint jwtAuthEntryPoint;
  
  
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
      .headers(headers -> headers.frameOptions(frame -> frame.disable()))
      .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests(requests ->
        requests
// ------------------------- SECTION .permitAll() -------------------------

          .requestMatchers("/usuarios/login").permitAll()
          .requestMatchers("/registrador/login").permitAll()
          .requestMatchers("/administrador-electoral/login").permitAll()
          .requestMatchers("/consejo-nacional/login").permitAll()
          .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()

// ------------------------- END SECTION .permitAll() -------------------------
// ------------------------- SECTION .hasAuthority(REGISTRADOR) -------------------------

          .requestMatchers("/candidato/**").hasAnyAuthority(
            TipoRol.REGISTRADOR.name(),
            TipoRol.CONSEJO_NACIONAL.name()
          )
          .requestMatchers("/partido/**").hasAnyAuthority(
            TipoRol.REGISTRADOR.name(),
            TipoRol.CONSEJO_NACIONAL.name()
          )

// ------------------------- END SECTION .hasAuthority(REGISTRADOR) -------------------------
// ------------------------- SECTION .hasAuthority(ADMINISTRADOR_ELECTORAL) -------------------------

          .requestMatchers("/eleccion/**").hasAuthority(TipoRol.ADMINISTRADOR_ELECTORAL.name())
          .requestMatchers("/administrador-electoral/**").hasAuthority(TipoRol.ADMINISTRADOR_ELECTORAL.name())

// ------------------------- END SECTION .hasAuthority(ADMINISTRADOR_ELECTORAL) -------------------------
// ------------------------- SECTION .hasAuthority(REGISTRADOR) - CENTRO VOTACION Y MESA -------------------------

          .requestMatchers("/registrador/**").hasAuthority(TipoRol.REGISTRADOR.name())
          .requestMatchers(HttpMethod.POST, "/centro-votacion/**").hasAuthority(TipoRol.REGISTRADOR.name())
          .requestMatchers(HttpMethod.PUT, "/centro-votacion/**").hasAuthority(TipoRol.REGISTRADOR.name())
          .requestMatchers(HttpMethod.DELETE, "/centro-votacion/**").hasAuthority(TipoRol.REGISTRADOR.name())
          .requestMatchers(HttpMethod.POST, "/mesa/**").hasAuthority(TipoRol.REGISTRADOR.name())
          .requestMatchers(HttpMethod.PUT, "/mesa/**").hasAuthority(TipoRol.REGISTRADOR.name())
          .requestMatchers(HttpMethod.DELETE, "/mesa/**").hasAuthority(TipoRol.REGISTRADOR.name())

// ------------------------- END SECTION .hasAuthority(REGISTRADOR) - CENTRO VOTACION Y MESA -------------------------
// ------------------------- SECTION .hasAuthority(CONSEJO_NACIONAL) -------------------------

          .requestMatchers("/consejo-nacional/**").hasAuthority(TipoRol.CONSEJO_NACIONAL.name())

// ------------------------- END SECTION .hasAuthority(CONSEJO_NACIONAL) -------------------------
          .anyRequest().permitAll()
      )
      .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthEntryPoint));

    http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

  @Bean
  public AuthenticationManager authenticationManager(
    AuthenticationConfiguration authenticationConfiguration
  ) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public JWTAuthenticationFilter jwtAuthenticationFilter() { return new JWTAuthenticationFilter(); } 
}
