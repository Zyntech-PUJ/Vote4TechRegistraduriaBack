package PortalRegistraduriaBack.services.candidato;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PortalRegistraduriaBack.dtos.candidato.CreateCandidatoDTO;
import PortalRegistraduriaBack.dtos.candidato.MapperCandidato;
import PortalRegistraduriaBack.dtos.candidato.ResponseCandidatoDTO;
import PortalRegistraduriaBack.dtos.candidato.UpdateCandidatoDTO;
import PortalRegistraduriaBack.entities.Candidato;
import PortalRegistraduriaBack.entities.Registrador;
import PortalRegistraduriaBack.exceptions.ResourceNotFoundException;
import PortalRegistraduriaBack.repositories.RepositoryCandidato;
import PortalRegistraduriaBack.repositories.RepositoryRegistrador;

@Service
public class ServiceCandidato implements IServiceCandidato {

  @Autowired
  RepositoryCandidato repositoryCandidato;

  @Autowired
  RepositoryRegistrador repositoryRegistrador;

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

    Candidato candidato = mapperCandidato.toEntity(candidatoDTO);
    candidato.setRegistrador(registrador);

    return mapperCandidato.toResponseDTO(repositoryCandidato.save(candidato));
  }

  @Override
  public ResponseCandidatoDTO updateCandidato(UpdateCandidatoDTO candidatoDTO) {
    Candidato candidatoUpdate = mapperCandidato.toEntity(candidatoDTO);

    return mapperCandidato.toResponseDTO(candidatoUpdate);
  }

  @Override
  public ResponseCandidatoDTO updateCandidato(Long id, UpdateCandidatoDTO candidatoDTO) {
    Candidato candidato = repositoryCandidato.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Candidato no encontrado con id: " + id));

    Registrador registrador = repositoryRegistrador.findById(candidatoDTO.getIdRegistrador())
        .orElseThrow(() -> new ResourceNotFoundException(
            "Registrador no encontrado con id: " + candidatoDTO.getIdRegistrador()));

    candidato.setNombre(candidatoDTO.getNombre());
    candidato.setNumero(candidatoDTO.getNumero());
    candidato.setFotoUrl(candidatoDTO.getFotoUrl());
    candidato.setPartidoLogoUrl(candidatoDTO.getPartidoLogoUrl());
    candidato.setRegistrador(registrador);

    return mapperCandidato.toResponseDTO(repositoryCandidato.save(candidato));
  }

  @Override
  public void deleteById(Long id) {
    if (!repositoryCandidato.existsById(id))
      throw new ResourceNotFoundException("Candidato no encontrado con id: " + id);
    repositoryCandidato.deleteById(id);
  }

}
