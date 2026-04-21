package PortalRegistraduriaBack.services.registrador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

@Service
public class ServiceRegistrador implements IServiceRegistrador {

  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Autowired
  RepositoryRegistrador repositoryRegistrador;

  @Autowired
  MapperRegistrador mapperRegistrador;

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
  public ResponseRegistradorDTO login(LoginRegistradorDTO loginDTO) {
    Registrador registrador = repositoryRegistrador.findByUsuario(loginDTO.getUsuario());

    if(registrador == null)
      throw new ResourceNotFoundException("Registrador no encontrado con usuario: " + loginDTO.getUsuario());

    if (!passwordEncoder.matches(loginDTO.getPassword(), registrador.getPassword()))
      throw new ResourceNotFoundException("Contraseña incorrecta para usuario: " + loginDTO.getUsuario());

    return mapperRegistrador.toResponseDTO(registrador);
  }

}
