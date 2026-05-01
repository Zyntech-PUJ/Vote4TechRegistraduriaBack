package PortalRegistraduriaBack.services.consejonacional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import PortalRegistraduriaBack.dtos.consejonacional.CreateConsejoNacionalDTO;
import PortalRegistraduriaBack.dtos.consejonacional.LoginConsejoNacionalDTO;
import PortalRegistraduriaBack.dtos.consejonacional.MapperConsejoNacional;
import PortalRegistraduriaBack.dtos.consejonacional.ResponseConsejoNacionalDTO;
import PortalRegistraduriaBack.dtos.consejonacional.UpdateConsejoNacionalDTO;
import PortalRegistraduriaBack.entities.ConsejoNacional;
import PortalRegistraduriaBack.entities.UsuarioEntity;
import PortalRegistraduriaBack.exceptions.BusinessException;
import PortalRegistraduriaBack.exceptions.ResourceNotFoundException;
import PortalRegistraduriaBack.repositories.RepositoryConsejoNacional;
import PortalRegistraduriaBack.repositories.RepositoryUsuarioEntity;
import PortalRegistraduriaBack.security.CustomUsuarioDetailService;
import PortalRegistraduriaBack.security.JWTGenerator;

@Service
public class ServiceConsejoNacional implements IServiceConsejoNacional {

  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Autowired
  private RepositoryConsejoNacional repositoryConsejoNacional;

  @Autowired
  private RepositoryUsuarioEntity repositoryUsuarioEntity;

  @Autowired
  private MapperConsejoNacional mapperConsejoNacional;

  @Autowired
  private JWTGenerator jwtGenerator;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private CustomUsuarioDetailService customUsuarioDetailService;

  @Override
  public List<ResponseConsejoNacionalDTO> findAll() {
    return mapperConsejoNacional.toResponseDTOs(repositoryConsejoNacional.findAll());
  }

  @Override
  public ResponseConsejoNacionalDTO findById(Long id) {
    ConsejoNacional consejoNacional = repositoryConsejoNacional.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(
            "Consejo nacional no encontrado con id: " + id));

    return mapperConsejoNacional.toResponseDTO(consejoNacional);
  }

  @Override
  public ResponseConsejoNacionalDTO addConsejoNacional(CreateConsejoNacionalDTO consejoNacionalDTO) {
    if (repositoryUsuarioEntity.existsByUsuario(consejoNacionalDTO.getUsername())) {
      throw new BusinessException(
          "Ya existe un usuario con username: " + consejoNacionalDTO.getUsername());
    }

    ConsejoNacional consejoNacional = mapperConsejoNacional.toEntity(consejoNacionalDTO);
    consejoNacional.setPassword(passwordEncoder.encode(consejoNacionalDTO.getPassword()));

    UsuarioEntity usuarioEntity = customUsuarioDetailService.consejoNacionalToUsuarioEntity(consejoNacional);
    usuarioEntity = repositoryUsuarioEntity.save(usuarioEntity);

    consejoNacional.setUsuarioEntity(usuarioEntity);

    return mapperConsejoNacional.toResponseDTO(repositoryConsejoNacional.save(consejoNacional));
  }

  @Override
  public ResponseConsejoNacionalDTO updateConsejoNacional(UpdateConsejoNacionalDTO consejoNacionalDTO) {
    ConsejoNacional consejoNacional = repositoryConsejoNacional.findById(
        consejoNacionalDTO.getIdConsejoNacional())
        .orElseThrow(() -> new ResourceNotFoundException(
            "Consejo nacional no encontrado con id: " + consejoNacionalDTO.getIdConsejoNacional()));

    return saveUpdatedConsejoNacional(consejoNacional, consejoNacionalDTO);
  }

  @Override
  public ResponseConsejoNacionalDTO updateConsejoNacional(Long id, UpdateConsejoNacionalDTO consejoNacionalDTO) {
    ConsejoNacional consejoNacional = repositoryConsejoNacional.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(
            "Consejo nacional no encontrado con id: " + id));

    return saveUpdatedConsejoNacional(consejoNacional, consejoNacionalDTO);
  }

  @Override
  public void deleteById(Long id) {
    ConsejoNacional consejoNacional = repositoryConsejoNacional.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(
            "Consejo nacional no encontrado con id: " + id));

    repositoryConsejoNacional.deleteById(id);

    if (consejoNacional.getUsuarioEntity() != null) {
      repositoryUsuarioEntity.deleteById(consejoNacional.getUsuarioEntity().getIdUsuarioEntity());
    }
  }

  @Override
  public String login(LoginConsejoNacionalDTO loginDTO) {
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);
    return jwtGenerator.generateToken(authentication);
  }

  private ResponseConsejoNacionalDTO saveUpdatedConsejoNacional(
      ConsejoNacional consejoNacional,
      UpdateConsejoNacionalDTO consejoNacionalDTO) {
    String currentUsername = consejoNacional.getUsername();
    String newUsername = consejoNacionalDTO.getUsername();

    if (!currentUsername.equals(newUsername) && repositoryUsuarioEntity.existsByUsuario(newUsername)) {
      throw new BusinessException("Ya existe un usuario con username: " + newUsername);
    }

    consejoNacional.setUsername(newUsername);
    consejoNacional.setPassword(passwordEncoder.encode(consejoNacionalDTO.getPassword()));

    UsuarioEntity usuarioEntity = consejoNacional.getUsuarioEntity();
    if (usuarioEntity == null) {
      usuarioEntity = customUsuarioDetailService.consejoNacionalToUsuarioEntity(consejoNacional);
    } else {
      usuarioEntity.setUsuario(consejoNacional.getUsername());
      usuarioEntity.setPassword(consejoNacional.getPassword());
    }

    usuarioEntity = repositoryUsuarioEntity.save(usuarioEntity);
    consejoNacional.setUsuarioEntity(usuarioEntity);

    return mapperConsejoNacional.toResponseDTO(repositoryConsejoNacional.save(consejoNacional));
  }
}
