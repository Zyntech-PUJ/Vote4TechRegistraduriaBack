package PortalRegistraduriaBack.services.administradorelectoral;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import PortalRegistraduriaBack.dtos.administradorelectoral.CreateAdministradorElectoralDTO;
import PortalRegistraduriaBack.dtos.administradorelectoral.LoginAdministradorElectoralDTO;
import PortalRegistraduriaBack.dtos.administradorelectoral.MapperAdministradorElectoral;
import PortalRegistraduriaBack.dtos.administradorelectoral.ResponseAdministradorElectoralDTO;
import PortalRegistraduriaBack.dtos.administradorelectoral.UpdateAdministradorElectoralDTO;
import PortalRegistraduriaBack.entities.AdministradorElectoral;
import PortalRegistraduriaBack.entities.UsuarioEntity;
import PortalRegistraduriaBack.exceptions.BusinessException;
import PortalRegistraduriaBack.exceptions.ResourceNotFoundException;
import PortalRegistraduriaBack.repositories.RepositoryAdministradorElectoral;
import PortalRegistraduriaBack.repositories.RepositoryUsuarioEntity;
import PortalRegistraduriaBack.security.CustomUsuarioDetailService;
import PortalRegistraduriaBack.security.JWTGenerator;

@Service
public class ServiceAdministradorElectoral implements IServiceAdministradorElectoral {

  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Autowired
  private RepositoryAdministradorElectoral repositoryAdministradorElectoral;

  @Autowired
  private RepositoryUsuarioEntity repositoryUsuarioEntity;

  @Autowired
  private MapperAdministradorElectoral mapperAdministradorElectoral;

  @Autowired
  private JWTGenerator jwtGenerator;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private CustomUsuarioDetailService customUsuarioDetailService;

  @Override
  public List<ResponseAdministradorElectoralDTO> findAll() {
    return mapperAdministradorElectoral.toResponseDTOs(repositoryAdministradorElectoral.findAll());
  }

  @Override
  public ResponseAdministradorElectoralDTO findById(Long id) {
    AdministradorElectoral administradorElectoral = repositoryAdministradorElectoral
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Administrador electoral no encontrado con id: " + id));

    return mapperAdministradorElectoral.toResponseDTO(administradorElectoral);
  }

  @Override
  public ResponseAdministradorElectoralDTO addAdministradorElectoral(
      CreateAdministradorElectoralDTO administradorElectoralDTO) {
    if (repositoryUsuarioEntity.existsByUsuario(administradorElectoralDTO.getUsuario()))
      throw new BusinessException("Ya existe un usuario con username: " + administradorElectoralDTO.getUsuario());

    AdministradorElectoral administradorElectoral = mapperAdministradorElectoral.toEntity(administradorElectoralDTO);
    administradorElectoral.setPassword(passwordEncoder.encode(administradorElectoralDTO.getPassword()));

    UsuarioEntity usuarioEntity = customUsuarioDetailService
        .administradorElectoralToUsuarioEntity(administradorElectoral);
    usuarioEntity = repositoryUsuarioEntity.save(usuarioEntity);

    administradorElectoral.setUsuarioEntity(usuarioEntity);

    return mapperAdministradorElectoral.toResponseDTO(
        repositoryAdministradorElectoral.save(administradorElectoral));
  }

  @Override
  public ResponseAdministradorElectoralDTO updateAdministradorElectoral(
      UpdateAdministradorElectoralDTO administradorElectoralDTO) {
    AdministradorElectoral administradorElectoral = repositoryAdministradorElectoral
        .findById(administradorElectoralDTO.getIdAdministradorElectoral())
        .orElseThrow(() -> new ResourceNotFoundException(
            "Administrador electoral no encontrado con id: "
                + administradorElectoralDTO.getIdAdministradorElectoral()));

    return saveUpdatedAdministradorElectoral(administradorElectoral, administradorElectoralDTO);
  }

  @Override
  public ResponseAdministradorElectoralDTO updateAdministradorElectoral(
      Long id,
      UpdateAdministradorElectoralDTO administradorElectoralDTO) {
    AdministradorElectoral administradorElectoral = repositoryAdministradorElectoral.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(
            "Administrador electoral no encontrado con id: " + id));

    return saveUpdatedAdministradorElectoral(administradorElectoral, administradorElectoralDTO);
  }

  @Override
  public void deleteById(Long id) {
    AdministradorElectoral administradorElectoral = repositoryAdministradorElectoral.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(
            "Administrador electoral no encontrado con id: " + id));

    repositoryAdministradorElectoral.deleteById(id);

    if (administradorElectoral.getUsuarioEntity() != null) {
      repositoryUsuarioEntity.deleteById(administradorElectoral.getUsuarioEntity().getIdUsuarioEntity());
    }
  }

  @Override
  public String login(LoginAdministradorElectoralDTO loginDTO) {
    try {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginDTO.getUsuario(), loginDTO.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    return jwtGenerator.generateToken(authentication);
    } catch(Exception e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  private ResponseAdministradorElectoralDTO saveUpdatedAdministradorElectoral(
      AdministradorElectoral administradorElectoral,
      UpdateAdministradorElectoralDTO administradorElectoralDTO) {
    String currentUsername = administradorElectoral.getUsuario();
    String newUsername = administradorElectoralDTO.getUsuario();

    if (!currentUsername.equals(newUsername) && repositoryUsuarioEntity.existsByUsuario(newUsername)) {
      throw new BusinessException("Ya existe un usuario con username: " + newUsername);
    }

    administradorElectoral.setUsuario(newUsername);
    administradorElectoral.setPassword(passwordEncoder.encode(administradorElectoralDTO.getPassword()));

    UsuarioEntity usuarioEntity = administradorElectoral.getUsuarioEntity();
    if (usuarioEntity == null) {
      usuarioEntity = customUsuarioDetailService.administradorElectoralToUsuarioEntity(administradorElectoral);
    } else {
      usuarioEntity.setUsuario(administradorElectoral.getUsuario());
      usuarioEntity.setPassword(administradorElectoral.getPassword());
    }

    usuarioEntity = repositoryUsuarioEntity.save(usuarioEntity);
    administradorElectoral.setUsuarioEntity(usuarioEntity);

    return mapperAdministradorElectoral.toResponseDTO(
        repositoryAdministradorElectoral.save(administradorElectoral));
  }
}
