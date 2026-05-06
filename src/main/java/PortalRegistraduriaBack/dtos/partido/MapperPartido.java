package PortalRegistraduriaBack.dtos.partido;

import java.util.List;

import org.springframework.stereotype.Component;

import PortalRegistraduriaBack.entities.Partido;

@Component
public class MapperPartido {
  
  public Partido toEntity(CreatePartidoDTO dto) {
    if (dto == null) return null;

    Partido partido = new Partido();
    partido.setNombre(dto.getNombre());
    partido.setSigla(dto.getSigla());
    partido.setActivo(dto.getActivo() != null ? dto.getActivo() : true);

    return partido;
  }

  public Partido toEntity(UpdatePartidoDTO dto) {
    if (dto == null) return null;

    Partido partido = new Partido();
    partido.setIdPartido(dto.getIdPartido());
    partido.setNombre(dto.getNombre());
    partido.setSigla(dto.getSigla());
    partido.setActivo(dto.getActivo());
  
    return partido;
  }
  
  public ResponsePartidoDTO toResponseDTO(Partido partido) {
    if (partido == null) return null;

    ResponsePartidoDTO dto = new ResponsePartidoDTO();
    dto.setIdPartido(partido.getIdPartido());
    dto.setNombre(partido.getNombre());
    dto.setSigla(partido.getSigla());
    dto.setActivo(partido.getActivo());
    dto.setIdRegistrador(partido.getRegistrador() != null ? partido.getRegistrador().getIdRegistrador() : null);

    return dto;
  }

  public List<ResponsePartidoDTO> toResponseDTOs(List<Partido> partidos) {
    return partidos
      .stream()
      .map(this::toResponseDTO)
      .toList();
  }
  
}
