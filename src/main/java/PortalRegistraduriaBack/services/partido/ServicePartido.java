package PortalRegistraduriaBack.services.partido;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import PortalRegistraduriaBack.dtos.partido.CreatePartidoDTO;
import PortalRegistraduriaBack.dtos.partido.MapperPartido;
import PortalRegistraduriaBack.dtos.partido.ResponsePartidoDTO;
import PortalRegistraduriaBack.entities.Partido;
import PortalRegistraduriaBack.entities.Registrador;
import PortalRegistraduriaBack.exceptions.BadRequestException;
import PortalRegistraduriaBack.exceptions.ResourceNotFoundException;
import PortalRegistraduriaBack.repositories.RepositoryPartido;
import PortalRegistraduriaBack.repositories.RepositoryRegistrador;

@Service
public class ServicePartido implements IServicePartido {

  @Autowired
  RepositoryPartido repositoryPartido;

  @Autowired
  RepositoryRegistrador repositoryRegistrador;

  @Autowired
  MapperPartido mapperPartido;

  @Override
  public List<ResponsePartidoDTO> findAll() {
    return mapperPartido.toResponseDTOs(repositoryPartido.findAll());
  }

  @Override
  public ResponsePartidoDTO findById(Long id) {
    Partido partido = repositoryPartido
      .findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Partido no encontrado id: " + id));
    return mapperPartido.toResponseDTO(partido);
  }

  @Override
  public ResponsePartidoDTO addPartido(
    CreatePartidoDTO partidoDTO,
    MultipartFile logo,
    MultipartFile estatutos,
    MultipartFile plataforma,
    MultipartFile registro,
    MultipartFile certificado
  ) {
    
    Registrador registrador = repositoryRegistrador
      .findById(partidoDTO.getIdRegistrador())
      .orElseThrow(() -> new ResourceNotFoundException("Registrador no encontrado con id: " + partidoDTO.getIdRegistrador()));

    Partido partido = mapperPartido.toEntity(partidoDTO);
    partido.setRegistrador(registrador);
    partido.setFechaCreacion(LocalDateTime.now());

    try {
      partido.setLogo(logo.getBytes());
      partido.setEstatutos(estatutos.getBytes());
      partido.setPlataformaIdeologica(plataforma.getBytes());
      partido.setRegistroAfiliadosDirectivos(registro.getBytes());
      partido.setCertificadoRepresentatividad(certificado.getBytes());
    } catch(IOException e) {
      throw new BadRequestException("Error procesando las imagenes/archivos");
    }

    return mapperPartido.toResponseDTO(repositoryPartido.save(partido));
  }

  @Override
  public void deleteById(Long id) {
    repositoryPartido.deleteById(id);
  }
  
}
