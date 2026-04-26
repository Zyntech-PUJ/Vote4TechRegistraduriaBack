package PortalRegistraduriaBack.security;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTGenerator {
  
  private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
  private static final Long EXPIRATION_TIME = 7000000L;
  
  public String generateToken(Authentication authentication) {

    String usuario = authentication.getName();
    Date fechaActual = new Date();
    Date fechaExpiracion = new Date(fechaActual.getTime() + EXPIRATION_TIME);

    List<String> roles = authentication
      .getAuthorities()
      .stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.toList());

    return Jwts.builder()
      .setSubject(usuario)
      .setIssuedAt(fechaActual)
      .setExpiration(fechaExpiracion)
      .claim("roles", roles)
      .signWith(key, SignatureAlgorithm.HS512)
      .compact();
  }
  
  public String getUsuarioFromJwt(String token) {
    return Jwts.parserBuilder()
      .setSigningKey(key)
      .build()
      .parseClaimsJws(token)
      .getBody()
      .getSubject();
  }
  
  public Boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch(Exception e) {
      return false;
    }
  }
  
}
