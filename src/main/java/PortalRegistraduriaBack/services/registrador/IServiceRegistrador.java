package PortalRegistraduriaBack.services.registrador;

import java.util.List;

import PortalRegistraduriaBack.dtos.registrador.CreateRegistradorDTO;
import PortalRegistraduriaBack.dtos.registrador.ResponseRegistradorDTO;
import PortalRegistraduriaBack.dtos.registrador.UpdateRegistradorDTO;


public interface IServiceRegistrador {

  public List<ResponseRegistradorDTO> findAll();
  public ResponseRegistradorDTO findById(Long id);
  public ResponseRegistradorDTO addRegistrador(CreateRegistradorDTO registradorDTO);
  public ResponseRegistradorDTO updateRegistrador(UpdateRegistradorDTO registradorDTO);
  public ResponseRegistradorDTO updateRegistrador(Long id, UpdateRegistradorDTO registradorDTO);
  public void deleteById(Long id);

}
