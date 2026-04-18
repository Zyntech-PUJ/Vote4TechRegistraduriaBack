package PortalRegistraduriaBack.services.eleccionjurado;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PortalRegistraduriaBack.dtos.eleccionjurado.CreateEleccionJuradoDTO;
import PortalRegistraduriaBack.dtos.eleccionjurado.MapperEleccionJurado;
import PortalRegistraduriaBack.dtos.eleccionjurado.ResponseEleccionJuradoDTO;
import PortalRegistraduriaBack.entities.Ciudadano;
import PortalRegistraduriaBack.entities.Eleccion;
import PortalRegistraduriaBack.entities.EleccionJurado;
import PortalRegistraduriaBack.enums.TipoJurado;
import PortalRegistraduriaBack.exceptions.BusinessException;
import PortalRegistraduriaBack.exceptions.ResourceNotFoundException;
import PortalRegistraduriaBack.repositories.RepositoryCiudadano;
import PortalRegistraduriaBack.repositories.RepositoryEleccion;
import PortalRegistraduriaBack.repositories.RepositoryEleccionJurado;

@Service
public class ServiceEleccionJurado implements IServiceEleccionJurado {

  @Autowired
  RepositoryEleccionJurado repositoryEleccionJurado;

  @Autowired
  RepositoryEleccion repositoryEleccion;

  @Autowired
  RepositoryCiudadano repositoryCiudadano;

  @Autowired
  MapperEleccionJurado mapperEleccionJurado;

  @Override
  public List<ResponseEleccionJuradoDTO> findAll() {
    return mapperEleccionJurado.toResponseDTOs(repositoryEleccionJurado.findAll());
  }

  @Override
  public List<ResponseEleccionJuradoDTO> findByEleccion(Long idEleccion) {
    validarEleccionExiste(idEleccion);
    return mapperEleccionJurado.toResponseDTOs(
        repositoryEleccionJurado.findByEleccion_IdEleccionOrderByNumeroMesaAscIdAsignacionJuradoAsc(idEleccion));
  }

  @Override
  public ResponseEleccionJuradoDTO addEleccionJurado(Long idEleccion, CreateEleccionJuradoDTO eleccionJuradoDTO) {
    Eleccion eleccion = repositoryEleccion.findById(idEleccion)
        .orElseThrow(() -> new ResourceNotFoundException("La eleccion con id " + idEleccion + " no existe."));

    if (eleccionJuradoDTO.getNumeroMesa() == null || eleccionJuradoDTO.getNumeroMesa() < 1
        || eleccionJuradoDTO.getNumeroMesa() > 32) {
      throw new BusinessException("El numero de mesa debe estar entre 1 y 32.");
    }

    Ciudadano ciudadano = repositoryCiudadano.findByCedula(eleccionJuradoDTO.getCedulaCiudadano())
        .orElseThrow(() -> new ResourceNotFoundException(
            "No existe un ciudadano con cedula " + eleccionJuradoDTO.getCedulaCiudadano() + "."));

    if (repositoryEleccionJurado.existsByEleccion_IdEleccionAndCiudadano_Cedula(idEleccion, ciudadano.getCedula())) {
      throw new BusinessException(
          "El ciudadano con cedula " + ciudadano.getCedula() + " ya es jurado en esta eleccion.");
    }

    EleccionJurado eleccionJurado = mapperEleccionJurado.toEntity(eleccionJuradoDTO, ciudadano, eleccion);
    eleccionJurado.setAsignado(true);

    return mapperEleccionJurado.toResponseDTO(repositoryEleccionJurado.save(eleccionJurado));
  }

  @Override
  public List<ResponseEleccionJuradoDTO> generarSorteo(Long idEleccion) {
    Eleccion eleccion = repositoryEleccion.findById(idEleccion)
        .orElseThrow(() -> new ResourceNotFoundException("La eleccion con id " + idEleccion + " no existe."));

    if (repositoryEleccionJurado.existsByEleccion_IdEleccion(idEleccion)) {
      throw new BusinessException("La eleccion con id " + idEleccion + " ya tiene jurados sorteados.");
    }

    List<Ciudadano> ciudadanos = repositoryCiudadano.findAll();
    if (ciudadanos.isEmpty()) {
      throw new BusinessException("No hay ciudadanos registrados para realizar el sorteo.");
    }

    List<EleccionJurado> asignaciones = new ArrayList<>();
    for (Ciudadano ciudadano : ciudadanos) {
      EleccionJurado asignacion = new EleccionJurado();

      int aleatorio = ThreadLocalRandom.current().nextInt(0, 101); // genera entre 0 y 100
      TipoJurado tipo = aleatorio <= 49 ? TipoJurado.URNA : TipoJurado.DOMICILIO;

      asignacion.setCiudadano(ciudadano);
      asignacion.setEleccion(eleccion);
      asignacion.setTipoJurado(tipo);
      asignacion.setNumeroMesa(ThreadLocalRandom.current().nextInt(1, 33));
      asignacion.setFechaCapacitacion(LocalDateTime.now());
      asignacion.setAsignado(true);
      asignaciones.add(asignacion);
    }

    return mapperEleccionJurado.toResponseDTOs(repositoryEleccionJurado.saveAll(asignaciones));
  }

  private void validarEleccionExiste(Long idEleccion) {
    if (!repositoryEleccion.existsById(idEleccion)) {
      throw new ResourceNotFoundException("La eleccion con id " + idEleccion + " no existe.");
    }
  }
}
