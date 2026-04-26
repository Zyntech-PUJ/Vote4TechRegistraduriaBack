package PortalRegistraduriaBack.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private JWTGenerator jwtGenerator;

  @Autowired
  private CustomUsuarioDetailService customUsuarioDetailService;

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {

    String token = getJWT(request);

    if(token != null && jwtGenerator.validateToken(token)) {
      String usuario = jwtGenerator.getUsuarioFromJwt(token);
      UserDetails userDetails = customUsuarioDetailService.loadUserByUsername(usuario);

      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        userDetails,
        null,
      userDetails.getAuthorities()
      );

      authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    filterChain.doFilter(request, response);
  }

  private String getJWT(HttpServletRequest request) {
    String bearerWord = "Bearer ";
    String bearerToken = request.getHeader("Authorization");

    if(bearerToken != null && bearerToken.startsWith(bearerWord)) return bearerToken.substring(bearerWord.length());
    return null;
  }
  
}
