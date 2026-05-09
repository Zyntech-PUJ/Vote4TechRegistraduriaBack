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
import PortalRegistraduriaBack.dtos.partido.UpdatePartidoDTO;
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
  public ResponsePartidoDTO addPartido(CreatePartidoDTO partidoDTO) {
    Registrador registrador = repositoryRegistrador
      .findById(partidoDTO.getIdRegistrador())
      .orElseThrow(() -> new ResourceNotFoundException("Registrador no encontrado con id: " + partidoDTO.getIdRegistrador()));

    Partido partido = mapperPartido.toEntity(partidoDTO);
    partido.setRegistrador(registrador);
    partido.setFechaCreacion(LocalDateTime.now());

    return mapperPartido.toResponseDTO(repositoryPartido.save(partido));
  }

  @Override
  public Boolean updatePartidoLogo(Long id, MultipartFile logo) {
    updateArchivoPartido(id, logo, TipoArchivoPartido.LOGO);
    return true;
  }

  @Override
  public Boolean updatePartidoEstatutos(Long id, MultipartFile estatutos) {
    updateArchivoPartido(id, estatutos, TipoArchivoPartido.ESTATUTOS);
    return true;
  }

  @Override
  public Boolean updatePartidoPlataforma(Long id, MultipartFile plataforma) {
    updateArchivoPartido(id, plataforma, TipoArchivoPartido.PLATAFORMA);
    return true;
  }

  @Override
  public Boolean updatePartidoRegistro(Long id, MultipartFile registro) {
    updateArchivoPartido(id, registro, TipoArchivoPartido.REGISTRO);
    return true;
  }

  @Override
  public Boolean updatePartidoCertificado(Long id, MultipartFile certificado) {
    updateArchivoPartido(id, certificado, TipoArchivoPartido.CERTIFICADO);
    return true;
  }

  @Override
  public ResponsePartidoDTO updatePartido(UpdatePartidoDTO partidoDTO) {
    Partido partido = repositoryPartido.findById(partidoDTO.getIdPartido())
      .orElseThrow(() -> new ResourceNotFoundException(
        "Partido no encontrado con id: " + partidoDTO.getIdPartido()
      ));

    return updateAndSavePartido(partido, partidoDTO);
  }

  @Override
  public ResponsePartidoDTO updatePartido(Long id, UpdatePartidoDTO partidoDTO) {
    Partido partido = repositoryPartido.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Partido no encontrado con id: " + id));

    return updateAndSavePartido(partido, partidoDTO);
  }

  @Override
  public void deleteById(Long id) {
    repositoryPartido.deleteById(id);
  }

  @Override
  public byte[] findLogoById(Long id) {
    return repositoryPartido.findLogoById(id);
  }

  @Override
  public byte[] findEstatutosById(Long id) {
    return repositoryPartido.findEstatutosById(id);
  }

  @Override
  public byte[] findPlataformaIdeologicaById(Long id) {
    return repositoryPartido.findPlataformaIdeologicaById(id);
  }

  @Override
  public byte[] findRegistroAfiliadosDirectivosById(Long id) {
    return repositoryPartido.findRegistroAfiliadosDirectivosById(id);
  }

  @Override
  public byte[] findCertificadoRepresentatividadById(Long id) {
    return repositoryPartido.findCertificadoRepresentatividadById(id);
  }

  private ResponsePartidoDTO updateAndSavePartido(Partido partido, UpdatePartidoDTO partidoDTO) {
    if (partidoDTO.getIdRegistrador() != null) {
      Registrador registrador = repositoryRegistrador
        .findById(partidoDTO.getIdRegistrador())
        .orElseThrow(() -> new ResourceNotFoundException(
          "Registrador no encontrado con id: " + partidoDTO.getIdRegistrador()
        ));
      partido.setRegistrador(registrador);
    }

    if (partidoDTO.getNombre() != null) {
      partido.setNombre(partidoDTO.getNombre());
    }

    if (partidoDTO.getSigla() != null) {
      partido.setSigla(partidoDTO.getSigla());
    }

    if (partidoDTO.getActivo() != null) {
      partido.setActivo(partidoDTO.getActivo());
    }

    return mapperPartido.toResponseDTO(repositoryPartido.save(partido));
  }

  private ResponsePartidoDTO updateArchivoPartido(Long id, MultipartFile archivo, TipoArchivoPartido tipoArchivo) {
    Partido partido = repositoryPartido.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Partido no encontrado con id: " + id));

    if (archivo == null || archivo.isEmpty()) {
      throw new BadRequestException("El archivo enviado es obligatorio");
    }

    try {
      byte[] contenido = archivo.getBytes();

      switch (tipoArchivo) {
        case LOGO -> partido.setLogo(contenido);
        case ESTATUTOS -> partido.setEstatutos(contenido);
        case PLATAFORMA -> partido.setPlataformaIdeologica(contenido);
        case REGISTRO -> partido.setRegistroAfiliadosDirectivos(contenido);
        case CERTIFICADO -> partido.setCertificadoRepresentatividad(contenido);
      }
    } catch (IOException e) {
      throw new BadRequestException("Error procesando el archivo del partido");
    }

    return mapperPartido.toResponseDTO(repositoryPartido.save(partido));
  }

  private enum TipoArchivoPartido {
    LOGO,
    ESTATUTOS,
    PLATAFORMA,
    REGISTRO,
    CERTIFICADO
  }
  
}
