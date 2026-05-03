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
  public ResponseCandidatoDTO addCandidato(
    CreateCandidatoDTO candidatoDTO,
    MultipartFile foto,
    MultipartFile formularioE6,
    MultipartFile certificado,
    MultipartFile cedula,
    MultipartFile aval
  ) {
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

    try {
      candidato.setFoto(foto.getBytes());
      candidato.setFormularioE6(formularioE6.getBytes());
      candidato.setCertificadoConsejoEstado(certificado.getBytes());
      candidato.setCopiaCedula(cedula.getBytes());
      candidato.setDocumentoAval(aval.getBytes());
    } catch(IOException e) {
      throw new BadRequestException("Error procesando las imagenes");
    }

    return mapperCandidato.toResponseDTO(repositoryCandidato.save(candidato));
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

}
