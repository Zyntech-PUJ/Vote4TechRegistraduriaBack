package PortalRegistraduriaBack.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import PortalRegistraduriaBack.entities.Registrador;
import PortalRegistraduriaBack.entities.Rol;
import PortalRegistraduriaBack.entities.UsuarioEntity;
import PortalRegistraduriaBack.entities.AdministradorElectoral;
import PortalRegistraduriaBack.entities.ConsejoNacional;
import PortalRegistraduriaBack.enums.TipoRol;
import PortalRegistraduriaBack.repositories.RepositoryRol;
import PortalRegistraduriaBack.repositories.RepositoryUsuarioEntity;

@Service
public class CustomUsuarioDetailService implements UserDetailsService {

  @Autowired
  private RepositoryUsuarioEntity repositoryUsuarioEntity;

  @Autowired
  private RepositoryRol repositoryRol;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
  
    UsuarioEntity usuario = repositoryUsuarioEntity
      .findByUsuario(username)
      .orElseThrow(() -> new UsernameNotFoundException("Usuario " + username + " no encontrado"));

    UserDetails userDetails = new User(
      usuario.getUsuario(),
      usuario.getPassword(),
      mapToGrantedAuthorities(usuario.getRoles())
    );

    return userDetails;
  }
  
  private Collection<GrantedAuthority> mapToGrantedAuthorities(List<Rol> roles) {
    return roles
      .stream()
      .map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
      .collect(Collectors.toList());
  }
  
  
  public UsuarioEntity registradorToUsuarioEntity(Registrador registrador) {
    UsuarioEntity usuario = UsuarioEntity
      .builder()
      .usuario(registrador.getUsuario())
      .password(passwordEncoder.encode(registrador.getPassword()))
      .build();

    Rol rol = repositoryRol.findByNombre(TipoRol.REGISTRADOR.name()).get();
    usuario.setRoles(List.of(rol));

    return usuario;
  }

  public UsuarioEntity administradorElectoralToUsuarioEntity(AdministradorElectoral administradorElectoral) {
    UsuarioEntity usuario = UsuarioEntity
      .builder()
      .usuario(administradorElectoral.getUsuario())
      .password(administradorElectoral.getPassword())
      .build();

    Rol rol = repositoryRol.findByNombre(TipoRol.ADMINISTRADOR_ELECTORAL.name()).get();
    usuario.setRoles(List.of(rol));

    return usuario;
  }

  public UsuarioEntity consejoNacionalToUsuarioEntity(ConsejoNacional consejoNacional) {
    UsuarioEntity usuario = UsuarioEntity
      .builder()
      .usuario(consejoNacional.getUsername())
      .password(consejoNacional.getPassword())
      .build();

    Rol rol = repositoryRol.findByNombre(TipoRol.CONSEJO_NACIONAL.name()).get();
    usuario.setRoles(List.of(rol));

    return usuario;
  }
  
}
