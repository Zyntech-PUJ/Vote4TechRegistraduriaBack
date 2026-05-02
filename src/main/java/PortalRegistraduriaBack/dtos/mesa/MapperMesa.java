package PortalRegistraduriaBack.dtos.mesa;

import java.util.List;

import org.springframework.stereotype.Component;

import PortalRegistraduriaBack.entities.Mesa;
import PortalRegistraduriaBack.enums.TipoMesa;

@Component
public class MapperMesa {

  public Mesa toEntity(CreateMesaDTO dto) {
    if (dto == null) return null;

    Mesa mesa = new Mesa();
    mesa.setNumero(dto.getNumero());
    mesa.setTipo(TipoMesa.valueOf(dto.getTipo().toUpperCase()));
    mesa.setActivo(dto.getActivo() != null ? dto.getActivo() : true);

    return mesa;
  }

  public ResponseMesaDTO toResponseDTO(Mesa mesa) {
    if (mesa == null) return null;

    ResponseMesaDTO dto = new ResponseMesaDTO();
    dto.setIdMesa(mesa.getIdMesa());
    dto.setNumero(mesa.getNumero());
    dto.setTipo(mesa.getTipo() != null ? mesa.getTipo().name() : null);
    dto.setActivo(mesa.getActivo());

    if (mesa.getCentroVotacion() != null) {
      dto.setIdCentroVotacion(mesa.getCentroVotacion().getIdCentroVotacion());
      dto.setNombreCentroVotacion(mesa.getCentroVotacion().getNombre());
      dto.setCiudadCentroVotacion(mesa.getCentroVotacion().getCiudad());
      dto.setDepartamentoCentroVotacion(mesa.getCentroVotacion().getDepartamento());
    }

    return dto;
  }

  public List<ResponseMesaDTO> toResponseDTOs(List<Mesa> mesas) {
    return mesas.stream().map(this::toResponseDTO).toList();
  }

}
