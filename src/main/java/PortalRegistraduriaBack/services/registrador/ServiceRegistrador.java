package PortalRegistraduriaBack.services.registrador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import PortalRegistraduriaBack.dtos.registrador.CreateRegistradorDTO;
import PortalRegistraduriaBack.dtos.registrador.LoginRegistradorDTO;
import PortalRegistraduriaBack.dtos.registrador.MapperRegistrador;
import PortalRegistraduriaBack.dtos.registrador.ResponseRegistradorDTO;
import PortalRegistraduriaBack.dtos.registrador.UpdateRegistradorDTO;
import PortalRegistraduriaBack.entities.Registrador;
import PortalRegistraduriaBack.exceptions.ResourceNotFoundException;
import PortalRegistraduriaBack.repositories.RepositoryRegistrador;
import PortalRegistraduriaBack.security.JWTGenerator;

@Service
public class ServiceRegistrador implements IServiceRegistrador {

  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Autowired
  RepositoryRegistrador repositoryRegistrador;

  @Autowired
  MapperRegistrador mapperRegistrador;

  @Autowired
  private JWTGenerator jwtGenerator;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Override
  public List<ResponseRegistradorDTO> findAll() {
    return mapperRegistrador.toResponseDTOs(repositoryRegistrador.findAll());
  }

  @Override
  public ResponseRegistradorDTO findById(Long id) {
    Registrador registrador = repositoryRegistrador.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Registrador no encontrado con id: " + id));

    return mapperRegistrador.toResponseDTO(registrador);
  }

  @Override
  public ResponseRegistradorDTO addRegistrador(CreateRegistradorDTO registradorDTO) {
    Registrador registrador = mapperRegistrador.toEntity(registradorDTO);

    registrador.setPassword(passwordEncoder.encode(registradorDTO.getPassword())); // se encripta password

    return mapperRegistrador.toResponseDTO(repositoryRegistrador.save(registrador));
  }

  @Override
  public ResponseRegistradorDTO updateRegistrador(UpdateRegistradorDTO registradorDTO) {
    Registrador registradorUpdate = mapperRegistrador.toEntity(registradorDTO);

    registradorUpdate.setPassword(passwordEncoder.encode(registradorDTO.getPassword())); // se encripta password

    return mapperRegistrador.toResponseDTO(repositoryRegistrador.save(registradorUpdate));
  }

  @Override
  public ResponseRegistradorDTO updateRegistrador(Long id, UpdateRegistradorDTO registradorDTO) {
    Registrador registrador = repositoryRegistrador.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Registrador no encontrado con id: " + id));

    registrador.setNombre(registradorDTO.getNombre());
    registrador.setUsuario(registradorDTO.getUsuario());

    registrador.setPassword(passwordEncoder.encode(registradorDTO.getPassword())); // se encripta password

    return mapperRegistrador.toResponseDTO(repositoryRegistrador.save(registrador));
  }

  @Override
  public void deleteById(Long id) {
    if (!repositoryRegistrador.existsById(id))
      throw new ResourceNotFoundException("Registrador no encontrado con id: " + id);

    repositoryRegistrador.deleteById(id);
  }

  @Override
  public String login(LoginRegistradorDTO loginDTO) {
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(loginDTO.getUsuario(), loginDTO.getPassword())
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);
    return jwtGenerator.generateToken(authentication);
  }

}
