package PortalRegistraduriaBack.dtos.candidato;

import java.util.List;

import org.springframework.stereotype.Component;

import PortalRegistraduriaBack.entities.Candidato;

@Component
public class MapperCandidato {

  public Candidato toEntity(CreateCandidatoDTO dto) {
    if (dto == null) return null;

    Candidato candidato = new Candidato();
    candidato.setNombre(dto.getNombre());
    candidato.setNumero(dto.getNumero());
    candidato.setActivo(dto.getActivo());

    return candidato;
  }

  public Candidato toEntity(UpdateCandidatoDTO dto) {
    if (dto == null) return null;

    Candidato candidato = new Candidato();
    candidato.setIdCandidato(dto.getIdCandidato());
    candidato.setNombre(dto.getNombre());
    candidato.setNumero(dto.getNumero());
    candidato.setActivo(dto.getActivo());

    return candidato;
  }

  public ResponseCandidatoDTO toResponseDTO(Candidato candidato) {
    if (candidato == null) return null;

    ResponseCandidatoDTO dto = new ResponseCandidatoDTO();
    dto.setIdCandidato(candidato.getIdCandidato());
    dto.setNombre(candidato.getNombre());
    dto.setNumero(candidato.getNumero());
    dto.setActivo(candidato.getActivo());
    dto.setIdLista(candidato.getLista() != null ? candidato.getLista().getIdLista() : null);
    dto.setIdPartido(candidato.getPartido() != null ? candidato.getPartido().getIdPartido() : null);
    dto.setIdRegistrador(candidato.getRegistrador() != null ? candidato.getRegistrador().getIdRegistrador() : null);

    return dto;
  }

  public List<ResponseCandidatoDTO> toResponseDTOs(List<Candidato> candidatos) {
    return candidatos
      .stream()
      .map(this::toResponseDTO)
      .toList();
  }
}
