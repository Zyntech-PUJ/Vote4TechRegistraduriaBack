package PortalRegistraduriaBack.services.candidato;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import PortalRegistraduriaBack.dtos.candidato.CreateCandidatoDTO;
import PortalRegistraduriaBack.dtos.candidato.MapperCandidato;
import PortalRegistraduriaBack.dtos.candidato.ResponseCandidatoDTO;
import PortalRegistraduriaBack.dtos.candidato.UpdateCandidatoDTO;
import PortalRegistraduriaBack.entities.Candidato;
import PortalRegistraduriaBack.entities.Lista;
import PortalRegistraduriaBack.entities.Partido;
import PortalRegistraduriaBack.entities.Registrador;
import PortalRegistraduriaBack.exceptions.BadRequestException;
import PortalRegistraduriaBack.exceptions.ResourceNotFoundException;
import PortalRegistraduriaBack.repositories.RepositoryCandidato;
import PortalRegistraduriaBack.repositories.RepositoryLista;
import PortalRegistraduriaBack.repositories.RepositoryPartido;
import PortalRegistraduriaBack.repositories.RepositoryRegistrador;

@Service
public class ServiceCandidato implements IServiceCandidato {

  @Autowired
  RepositoryCandidato repositoryCandidato;

  @Autowired
  RepositoryRegistrador repositoryRegistrador;

  @Autowired
  RepositoryLista repositoryLista;

  @Autowired
  RepositoryPartido repositoryPartido;

  @Autowired
  MapperCandidato mapperCandidato;

  @Override
  public List<ResponseCandidatoDTO> findAll() {
    return mapperCandidato.toResponseDTOs(repositoryCandidato.findAll());
  }

  @Override
  public ResponseCandidatoDTO findById(Long id) {
    Candidato candidato = repositoryCandidato.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Candidato no encontrado con id: " + id));

    return mapperCandidato.toResponseDTO(candidato);
  }

  @Override
  public ResponseCandidatoDTO addCandidato(CreateCandidatoDTO candidatoDTO) {
    Registrador registrador = repositoryRegistrador.findById(candidatoDTO.getIdRegistrador())
        .orElseThrow(() -> new ResourceNotFoundException(
            "Registrador no encontrado con id: " + candidatoDTO.getIdRegistrador()));

    Lista lista = repositoryLista.findById(candidatoDTO.getIdLista())
        .orElseThrow(() -> new ResourceNotFoundException(
            "Lista no encontrada con id: " + candidatoDTO.getIdLista()));

    Partido partido = repositoryPartido.findById(candidatoDTO.getIdPartido())
        .orElseThrow(() -> new ResourceNotFoundException(
            "Partido no encontrado con id: " + candidatoDTO.getIdPartido()));

    Candidato candidato = mapperCandidato.toEntity(candidatoDTO);
    candidato.setRegistrador(registrador);
    candidato.setLista(lista);
    candidato.setPartido(partido);

    return mapperCandidato.toResponseDTO(repositoryCandidato.save(candidato));
  }

  @Override
  public void updateCandidatoFoto(Long id, MultipartFile foto) {
    updateArchivoCandidato(id, foto, TipoArchivoCandidato.FOTO);
  }

  @Override
  public void updateCandidatoFormularioE6(Long id, MultipartFile formularioE6) {
    updateArchivoCandidato(id, formularioE6, TipoArchivoCandidato.FORMULARIO_E6);
  }

  @Override
  public void updateCandidatoCertificado(Long id, MultipartFile certificado) {
    updateArchivoCandidato(id, certificado, TipoArchivoCandidato.CERTIFICADO);
  }

  @Override
  public void updateCandidatoCedula(Long id, MultipartFile cedula) {
    updateArchivoCandidato(id, cedula, TipoArchivoCandidato.CEDULA);
  }

  @Override
  public void updateCandidatoAval(Long id, MultipartFile aval) {
    updateArchivoCandidato(id, aval, TipoArchivoCandidato.AVAL);
  }

  @Override
  public ResponseCandidatoDTO updateCandidato(UpdateCandidatoDTO candidatoDTO) {
    Candidato candidato = repositoryCandidato.findById(candidatoDTO.getIdCandidato())
        .orElseThrow(() -> new ResourceNotFoundException(
            "Candidato no encontrado con id: " + candidatoDTO.getIdCandidato()));

    return updateAndSaveCandidato(candidato, candidatoDTO);
  }

  @Override
  public ResponseCandidatoDTO updateCandidato(Long id, UpdateCandidatoDTO candidatoDTO) {
    Candidato candidato = repositoryCandidato.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Candidato no encontrado con id: " + id));

    return updateAndSaveCandidato(candidato, candidatoDTO);
  }

  @Override
  public void deleteById(Long id) {
    if (!repositoryCandidato.existsById(id))
      throw new ResourceNotFoundException("Candidato no encontrado con id: " + id);
    repositoryCandidato.deleteById(id);
  }

  @Override
  public byte[] findFotoById(Long id) {
    return repositoryCandidato.findFotoById(id);
  }

  @Override
  public byte[] findFormularioE6ById(Long id) {
    return repositoryCandidato.findFormularioE6ById(id);
  }

  @Override
  public byte[] findCertificadoConsejoEstadoById(Long id) {
    return repositoryCandidato.findCertificadoConsejoEstadoById(id);
  }

  @Override
  public byte[] findCopiaCedulaById(Long id) {
    return repositoryCandidato.findCopiaCedulaById(id);
  }

  @Override
  public byte[] findDocumentoAvalById(Long id) {
    return repositoryCandidato.findDocumentoAvalById(id);
  }

  private ResponseCandidatoDTO updateAndSaveCandidato(Candidato candidato, UpdateCandidatoDTO candidatoDTO) {
    Registrador registrador = repositoryRegistrador.findById(candidatoDTO.getIdRegistrador())
        .orElseThrow(() -> new ResourceNotFoundException(
            "Registrador no encontrado con id: " + candidatoDTO.getIdRegistrador()));

    Lista lista = repositoryLista.findById(candidatoDTO.getIdLista())
        .orElseThrow(() -> new ResourceNotFoundException(
            "Lista no encontrada con id: " + candidatoDTO.getIdLista()));

    Partido partido = repositoryPartido.findById(candidatoDTO.getIdPartido())
        .orElseThrow(() -> new ResourceNotFoundException(
            "Partido no encontrado con id: " + candidatoDTO.getIdPartido()));

    candidato.setNombre(candidatoDTO.getNombre());
    candidato.setNumero(candidatoDTO.getNumero());
    candidato.setActivo(candidatoDTO.getActivo());
    candidato.setRegistrador(registrador);
    candidato.setLista(lista);
    candidato.setPartido(partido);

    return mapperCandidato.toResponseDTO(repositoryCandidato.save(candidato));
  }

  private void updateArchivoCandidato(Long id, MultipartFile archivo, TipoArchivoCandidato tipoArchivo) {
    Candidato candidato = repositoryCandidato.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Candidato no encontrado con id: " + id));

    if (archivo == null || archivo.isEmpty()) {
      throw new BadRequestException("El archivo enviado es obligatorio");
    }

    try {
      byte[] contenido = archivo.getBytes();

      switch (tipoArchivo) {
        case FOTO -> candidato.setFoto(contenido);
        case FORMULARIO_E6 -> candidato.setFormularioE6(contenido);
        case CERTIFICADO -> candidato.setCertificadoConsejoEstado(contenido);
        case CEDULA -> candidato.setCopiaCedula(contenido);
        case AVAL -> candidato.setDocumentoAval(contenido);
      }
    } catch (IOException e) {
      throw new BadRequestException("Error procesando el archivo del candidato");
    }

    repositoryCandidato.save(candidato);
  }

  private enum TipoArchivoCandidato {
    FOTO,
    FORMULARIO_E6,
    CERTIFICADO,
    CEDULA,
    AVAL
  }

}
