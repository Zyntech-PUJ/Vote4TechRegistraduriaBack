package PortalRegistraduriaBack.services.mesa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PortalRegistraduriaBack.dtos.mesa.CreateMesaDTO;
import PortalRegistraduriaBack.dtos.mesa.MapperMesa;
import PortalRegistraduriaBack.dtos.mesa.ResponseMesaDTO;
import PortalRegistraduriaBack.dtos.mesa.UpdateMesaDTO;
import PortalRegistraduriaBack.entities.CentroVotacion;
import PortalRegistraduriaBack.entities.Mesa;
import PortalRegistraduriaBack.enums.TipoMesa;
import PortalRegistraduriaBack.exceptions.BadRequestException;
import PortalRegistraduriaBack.exceptions.ResourceNotFoundException;
import PortalRegistraduriaBack.repositories.RepositoryCentroVotacion;
import PortalRegistraduriaBack.repositories.RepositoryMesa;

@Service
public class ServiceMesa implements IServiceMesa {

  @Autowired
  RepositoryMesa repositoryMesa;

  @Autowired
  RepositoryCentroVotacion repositoryCentroVotacion;

  @Autowired
  MapperMesa mapperMesa;

  @Override
  public List<ResponseMesaDTO> findAll() {
    return mapperMesa.toResponseDTOs(repositoryMesa.findAll());
  }

  @Override
  public ResponseMesaDTO findById(Long id) {
    Mesa mesa = repositoryMesa.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Mesa no encontrada con id: " + id));
    return mapperMesa.toResponseDTO(mesa);
  }

  @Override
  public List<ResponseMesaDTO> findByCentroVotacion(Long idCentroVotacion) {
    if (!repositoryCentroVotacion.existsById(idCentroVotacion))
      throw new ResourceNotFoundException(
          "Centro de votación no encontrado con id: " + idCentroVotacion);
    return mapperMesa.toResponseDTOs(
        repositoryMesa.findByCentroVotacion_IdCentroVotacion(idCentroVotacion));
  }

  @Override
  public ResponseMesaDTO addMesa(CreateMesaDTO dto) {
    CentroVotacion centro = repositoryCentroVotacion.findById(dto.getIdCentroVotacion())
        .orElseThrow(() -> new ResourceNotFoundException(
            "Centro de votación no encontrado con id: " + dto.getIdCentroVotacion()));

    TipoMesa tipo;
    try {
      tipo = TipoMesa.valueOf(dto.getTipo().toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new BadRequestException("Tipo de mesa inválido: " + dto.getTipo() + ". Valores permitidos: URNA, DOMICILIO");
    }

    Mesa mesa = mapperMesa.toEntity(dto);
    mesa.setTipo(tipo);
    mesa.setCentroVotacion(centro);

    return mapperMesa.toResponseDTO(repositoryMesa.save(mesa));
  }

  @Override
  public ResponseMesaDTO updateMesa(Long id, UpdateMesaDTO dto) {
    Mesa mesa = repositoryMesa.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Mesa no encontrada con id: " + id));

    if (dto.getNumero() != null) mesa.setNumero(dto.getNumero());
    if (dto.getActivo() != null) mesa.setActivo(dto.getActivo());

    if (dto.getTipo() != null) {
      try {
        mesa.setTipo(TipoMesa.valueOf(dto.getTipo().toUpperCase()));
      } catch (IllegalArgumentException e) {
        throw new BadRequestException("Tipo de mesa inválido: " + dto.getTipo() + ". Valores permitidos: URNA, DOMICILIO");
      }
    }

    if (dto.getIdCentroVotacion() != null) {
      CentroVotacion centro = repositoryCentroVotacion.findById(dto.getIdCentroVotacion())
          .orElseThrow(() -> new ResourceNotFoundException(
              "Centro de votación no encontrado con id: " + dto.getIdCentroVotacion()));
      mesa.setCentroVotacion(centro);
    }

    return mapperMesa.toResponseDTO(repositoryMesa.save(mesa));
  }

  @Override
  public void deleteById(Long id) {
    if (!repositoryMesa.existsById(id))
      throw new ResourceNotFoundException("Mesa no encontrada con id: " + id);
    repositoryMesa.deleteById(id);
  }

}
