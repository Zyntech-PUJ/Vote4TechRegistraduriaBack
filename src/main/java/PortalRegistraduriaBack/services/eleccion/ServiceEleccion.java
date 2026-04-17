package PortalRegistraduriaBack.services.eleccion;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PortalRegistraduriaBack.dtos.eleccion.CreateEleccionDTO;
import PortalRegistraduriaBack.dtos.eleccion.MapperEleccion;
import PortalRegistraduriaBack.dtos.eleccion.ResponseEleccionDTO;
import PortalRegistraduriaBack.dtos.eleccion.UpdateEleccionDTO;
import PortalRegistraduriaBack.entities.Eleccion;
import PortalRegistraduriaBack.entities.Registrador;
import PortalRegistraduriaBack.enums.EstadoEleccion;
import PortalRegistraduriaBack.exceptions.BadRequestException;
import PortalRegistraduriaBack.exceptions.BusinessException;
import PortalRegistraduriaBack.exceptions.ResourceNotFoundException;
import PortalRegistraduriaBack.repositories.RepositoryEleccion;
import PortalRegistraduriaBack.repositories.RepositoryRegistrador;

@Service
public class ServiceEleccion implements IServiceEleccion {

  @Autowired
  RepositoryEleccion repositoryEleccion;

  @Autowired
  RepositoryRegistrador repositoryRegistrador;

  @Autowired
  MapperEleccion mapperEleccion;

  @Override
  public List<ResponseEleccionDTO> findAll() {
    return mapperEleccion.toResponseDTOs(repositoryEleccion.findAll());
  }

  @Override
  public ResponseEleccionDTO findById(Long id) {
    return mapperEleccion.toResponseDTO(repositoryEleccion.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("La eleccion con id " + id + " no existe.")));
  }

 @Override
  public ResponseEleccionDTO addEleccion(CreateEleccionDTO eleccionDTO) {
    Registrador registrador = repositoryRegistrador.findById(eleccionDTO.getIdRegistrador())
      .orElseThrow(() -> new BadRequestException("El registrador con id" + eleccionDTO.getIdRegistrador() + "no existe."));

    Eleccion eleccion = mapperEleccion.toEntity(eleccionDTO);
    eleccion.setEstado(EstadoEleccion.CONFIGURACION);
    eleccion.setFechaCreacion(LocalDateTime.now());
    eleccion.setRegistrador(registrador);

    return mapperEleccion.toResponseDTO(repositoryEleccion.save(eleccion));
  }

  @Override
  public ResponseEleccionDTO updateEleccion(UpdateEleccionDTO eleccionDTO) {
    Eleccion eleccionUpdate = mapperEleccion.toEntity(eleccionDTO);
    return mapperEleccion.toResponseDTO(repositoryEleccion.save(eleccionUpdate));
  }

  @Override
  public ResponseEleccionDTO updateEleccion(Long id, UpdateEleccionDTO eleccionDTO) {
    Eleccion eleccion = repositoryEleccion.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No se encontró la elección con id: " + id));

    if (eleccion.getEstado() != EstadoEleccion.CONFIGURACION)
      throw new BusinessException("La elección solo se puede modificar en estado CONFIGURACION. Estado actual: " + eleccion.getEstado());

    Registrador registrador = repositoryRegistrador.findById(eleccionDTO.getIdRegistrador())
        .orElseThrow(() -> new BadRequestException("El registrador con id " + eleccionDTO.getIdRegistrador() + " no existe."));

    eleccion.setNombre(eleccionDTO.getNombre());
    eleccion.setFechaInicio(eleccionDTO.getFechaInicio());
    eleccion.setFechaFinalizacion(eleccionDTO.getFechaFinalizacion());
    eleccion.setTipo(eleccionDTO.getTipo());
    eleccion.setListaAbierta(eleccionDTO.getListaAbierta());
    eleccion.setRegistrador(registrador);

    return mapperEleccion.toResponseDTO(repositoryEleccion.save(eleccion));
  }

  @Override
  public void deleteById(Long id) {
    if (!repositoryEleccion.existsById(id))
      throw new ResourceNotFoundException("No se encontró la elección con id: " + id);

    repositoryEleccion.deleteById(id);
  }

  @Override
  public ResponseEleccionDTO lanzarEleccion(Long id) {
    Eleccion eleccion = repositoryEleccion.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No se encontró la elección con id: " + id));

    if (eleccion.getEstado() != EstadoEleccion.CONFIGURACION)
      throw new BusinessException("Solo se puede lanzar una elección en estado CONFIGURACION. Estado actual: " + eleccion.getEstado());

    eleccion.setEstado(EstadoEleccion.LANZADA);

    return mapperEleccion.toResponseDTO(repositoryEleccion.save(eleccion));
  }

  @Override
  public ResponseEleccionDTO finalizarEleccion(Long id) {
    Eleccion eleccion = repositoryEleccion.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No se encontró la elección con id: " + id));

    if (eleccion.getEstado() != EstadoEleccion.EN_CURSO)
      throw new BusinessException("Solo se puede finalizar una elección en estado EN_CURSO. Estado actual: " + eleccion.getEstado());

    eleccion.setEstado(EstadoEleccion.FINALIZADA);
    return mapperEleccion.toResponseDTO(repositoryEleccion.save(eleccion));
  }
  
}
