package PortalRegistraduriaBack.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PortalRegistraduriaBack.dtos.login.LoginDTO;
import PortalRegistraduriaBack.dtos.login.LoginResponseDTO;
import PortalRegistraduriaBack.security.CustomUsuarioDetailService;
import PortalRegistraduriaBack.security.JWTGenerator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private CustomUsuarioDetailService customUsuarioDetailService;

  @Autowired
  private JWTGenerator jwtGenerator;

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
          loginDTO.usuario(),
          loginDTO.password()
        )
    );

    UserDetails userDetails = customUsuarioDetailService.loadUserByUsername(loginDTO.usuario());

    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        userDetails, null, userDetails.getAuthorities());

    String token = jwtGenerator.generateToken(authentication);

    List<String> roles = userDetails.getAuthorities()
      .stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.toList());

    return ResponseEntity.ok(new LoginResponseDTO(token, loginDTO.usuario(), roles));
  }

}
