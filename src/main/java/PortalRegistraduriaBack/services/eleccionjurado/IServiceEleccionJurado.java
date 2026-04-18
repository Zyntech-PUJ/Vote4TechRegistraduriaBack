package PortalRegistraduriaBack.services.eleccionjurado;

import java.util.List;

import PortalRegistraduriaBack.dtos.eleccionjurado.CreateEleccionJuradoDTO;
import PortalRegistraduriaBack.dtos.eleccionjurado.ResponseEleccionJuradoDTO;

public interface IServiceEleccionJurado {

  List<ResponseEleccionJuradoDTO> findAll();
  List<ResponseEleccionJuradoDTO> findByEleccion(Long idEleccion);
  ResponseEleccionJuradoDTO addEleccionJurado(Long idEleccion, CreateEleccionJuradoDTO eleccionJuradoDTO);
  List<ResponseEleccionJuradoDTO> generarSorteo(Long idEleccion);
}
