package PortalRegistraduriaBack.services.mesa;

import java.util.List;

import PortalRegistraduriaBack.dtos.mesa.CreateMesaDTO;
import PortalRegistraduriaBack.dtos.mesa.ResponseMesaDTO;
import PortalRegistraduriaBack.dtos.mesa.UpdateMesaDTO;

public interface IServiceMesa {

  List<ResponseMesaDTO> findAll();

  ResponseMesaDTO findById(Long id);

  List<ResponseMesaDTO> findByCentroVotacion(Long idCentroVotacion);

  ResponseMesaDTO addMesa(CreateMesaDTO dto);

  ResponseMesaDTO updateMesa(Long id, UpdateMesaDTO dto);

  void deleteById(Long id);

}
