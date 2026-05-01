package PortalRegistraduriaBack.dtos.eleccion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import PortalRegistraduriaBack.dtos.administradorelectoral.MapperAdministradorElectoral;
import PortalRegistraduriaBack.entities.Eleccion;


@Component
public class MapperEleccion {
  
  @Autowired
  MapperAdministradorElectoral mapperAdministradorElectoral;
  
  public Eleccion toEntity(CreateEleccionDTO dto) {
    if (dto == null) return null;

    Eleccion eleccion = new Eleccion();
    eleccion.setNombre(dto.getNombre());
    eleccion.setFechaInicio(dto.getFechaInicio());
    eleccion.setFechaFinalizacion(dto.getFechaFinalizacion());
    eleccion.setTipo(dto.getTipo());
    eleccion.setListaAbierta(dto.getListaAbierta());

    return eleccion;
  }

  public Eleccion toEntity(UpdateEleccionDTO dto) {
    if (dto == null) return null;

    Eleccion eleccion = new Eleccion();
    eleccion.setIdEleccion(dto.getIdEleccion());
    eleccion.setNombre(dto.getNombre());
    eleccion.setFechaInicio(dto.getFechaInicio());
    eleccion.setFechaFinalizacion(dto.getFechaFinalizacion());
    eleccion.setTipo(dto.getTipo());
    eleccion.setListaAbierta(dto.getListaAbierta());

    return eleccion;
  }

  public ResponseEleccionDTO toResponseDTO(Eleccion eleccion) {
    if(eleccion == null) return null;

    ResponseEleccionDTO dto = new ResponseEleccionDTO();
    dto.setIdEleccion(eleccion.getIdEleccion());
    dto.setNombre(eleccion.getNombre());
    dto.setFechaInicio(eleccion.getFechaInicio());
    dto.setFechaFinalizacion(eleccion.getFechaFinalizacion());
    dto.setFechaCreacion(eleccion.getFechaCreacion());
    dto.setTipo(eleccion.getTipo());
    dto.setListaAbierta(eleccion.getListaAbierta());
    dto.setEstado(eleccion.getEstado());
    dto.setAdministradorElectoral(
      mapperAdministradorElectoral.toResponseDTO(eleccion.getAdministradorElectoral())
    );

    return dto; 
  }

  public List<ResponseEleccionDTO> toResponseDTOs(List<Eleccion> elecciones){
    return elecciones
      .stream()
      .map(this::toResponseDTO)
      .toList();
  }
}
