package PortalRegistraduriaBack.services.centrovotacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PortalRegistraduriaBack.dtos.centrovotacion.CreateCentroVotacionDTO;
import PortalRegistraduriaBack.dtos.centrovotacion.MapperCentroVotacion;
import PortalRegistraduriaBack.dtos.centrovotacion.ResponseCentroVotacionDTO;
import PortalRegistraduriaBack.dtos.centrovotacion.UpdateCentroVotacionDTO;
import PortalRegistraduriaBack.entities.CentroVotacion;
import PortalRegistraduriaBack.exceptions.ResourceNotFoundException;
import PortalRegistraduriaBack.repositories.RepositoryCentroVotacion;

@Service
public class ServiceCentroVotacion implements IServiceCentroVotacion {

  @Autowired
  RepositoryCentroVotacion repositoryCentroVotacion;

  @Autowired
  MapperCentroVotacion mapperCentroVotacion;

  @Override
  public List<ResponseCentroVotacionDTO> findAll() {
    return mapperCentroVotacion.toResponseDTOs(repositoryCentroVotacion.findAll());
  }

  @Override
  public ResponseCentroVotacionDTO findById(Long id) {
    CentroVotacion centro = repositoryCentroVotacion.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(
            "Centro de votación no encontrado con id: " + id));
    return mapperCentroVotacion.toResponseDTO(centro);
  }

  @Override
  public ResponseCentroVotacionDTO addCentroVotacion(CreateCentroVotacionDTO dto) {
    CentroVotacion centro = mapperCentroVotacion.toEntity(dto);
    return mapperCentroVotacion.toResponseDTO(repositoryCentroVotacion.save(centro));
  }

  @Override
  public ResponseCentroVotacionDTO updateCentroVotacion(Long id, UpdateCentroVotacionDTO dto) {
    CentroVotacion centro = repositoryCentroVotacion.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(
            "Centro de votación no encontrado con id: " + id));

    centro.setNombre(dto.getNombre());
    centro.setDireccion(dto.getDireccion());
    centro.setCiudad(dto.getCiudad());
    centro.setDepartamento(dto.getDepartamento());
    if (dto.getActivo() != null) centro.setActivo(dto.getActivo());

    return mapperCentroVotacion.toResponseDTO(repositoryCentroVotacion.save(centro));
  }

  @Override
  public void deleteById(Long id) {
    if (!repositoryCentroVotacion.existsById(id))
      throw new ResourceNotFoundException("Centro de votación no encontrado con id: " + id);
    repositoryCentroVotacion.deleteById(id);
  }

}
