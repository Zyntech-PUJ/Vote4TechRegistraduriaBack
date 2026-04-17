package PortalRegistraduriaBack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

  @Bean
  public CorsFilter corsFilter() {
    CorsConfiguration config = new CorsConfiguration();

    config.addAllowedOrigin("http://10.43.100.127");
    config.addAllowedOrigin("http://10.43.100.127:4200");
    config.addAllowedOrigin("http://10.43.100.131");
    config.addAllowedOrigin("http://10.43.100.131:4200");
    config.addAllowedOrigin("http://localhost:4200");

    config.addAllowedMethod("*"); // GET, POST, PUT, PATCH, DELETE, OPTIONS
    config.addAllowedHeader("*");
    config.setAllowCredentials(true);
    config.setMaxAge(3600L); // cachea el preflight 1 hora

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);

    return new CorsFilter(source);
  }
}