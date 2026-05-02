package PortalRegistraduriaBack.services.centrovotacion;

import java.util.List;

import PortalRegistraduriaBack.dtos.centrovotacion.CreateCentroVotacionDTO;
import PortalRegistraduriaBack.dtos.centrovotacion.ResponseCentroVotacionDTO;
import PortalRegistraduriaBack.dtos.centrovotacion.UpdateCentroVotacionDTO;

public interface IServiceCentroVotacion {

  List<ResponseCentroVotacionDTO> findAll();

  ResponseCentroVotacionDTO findById(Long id);

  ResponseCentroVotacionDTO addCentroVotacion(CreateCentroVotacionDTO dto);

  ResponseCentroVotacionDTO updateCentroVotacion(Long id, UpdateCentroVotacionDTO dto);

  void deleteById(Long id);

}
