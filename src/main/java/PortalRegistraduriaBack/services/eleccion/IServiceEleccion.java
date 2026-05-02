package PortalRegistraduriaBack.services.eleccion;

import java.util.List;

import PortalRegistraduriaBack.dtos.eleccion.CreateEleccionDTO;
import PortalRegistraduriaBack.dtos.eleccion.ResponseEleccionDTO;
import PortalRegistraduriaBack.dtos.eleccion.UpdateEleccionDTO;

public interface IServiceEleccion {

  public List<ResponseEleccionDTO> findAll();
  public ResponseEleccionDTO findById(Long id);
  public ResponseEleccionDTO addEleccion(CreateEleccionDTO eleccionDTO);
  public ResponseEleccionDTO updateEleccion(UpdateEleccionDTO eleccionDTO);
  public ResponseEleccionDTO updateEleccion(Long id, UpdateEleccionDTO eleccionDTO);
  public ResponseEleccionDTO lanzarEleccion(Long id);
  public ResponseEleccionDTO iniciarEleccion(Long id);
  public ResponseEleccionDTO finalizarEleccion(Long id);
  public void deleteById(Long id);

}
