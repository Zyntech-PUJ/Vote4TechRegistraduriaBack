package PortalRegistraduriaBack.dtos.centrovotacion;

import java.util.List;

import org.springframework.stereotype.Component;

import PortalRegistraduriaBack.entities.CentroVotacion;

@Component
public class MapperCentroVotacion {

  public CentroVotacion toEntity(CreateCentroVotacionDTO dto) {
    if (dto == null) return null;

    CentroVotacion centro = new CentroVotacion();
    centro.setNombre(dto.getNombre());
    centro.setDireccion(dto.getDireccion());
    centro.setCiudad(dto.getCiudad());
    centro.setDepartamento(dto.getDepartamento());
    centro.setActivo(dto.getActivo() != null ? dto.getActivo() : true);

    return centro;
  }

  public ResponseCentroVotacionDTO toResponseDTO(CentroVotacion centro) {
    if (centro == null) return null;

    ResponseCentroVotacionDTO dto = new ResponseCentroVotacionDTO();
    dto.setIdCentroVotacion(centro.getIdCentroVotacion());
    dto.setNombre(centro.getNombre());
    dto.setDireccion(centro.getDireccion());
    dto.setCiudad(centro.getCiudad());
    dto.setDepartamento(centro.getDepartamento());
    dto.setActivo(centro.getActivo());

    return dto;
  }

  public List<ResponseCentroVotacionDTO> toResponseDTOs(List<CentroVotacion> centros) {
    return centros.stream().map(this::toResponseDTO).toList();
  }

}
