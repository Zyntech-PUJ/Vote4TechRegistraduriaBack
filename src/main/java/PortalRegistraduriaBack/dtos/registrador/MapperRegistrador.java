package PortalRegistraduriaBack.dtos.registrador;

import java.util.List;

import org.springframework.stereotype.Component;

import PortalRegistraduriaBack.entities.Registrador;

@Component
public class MapperRegistrador {

  public Registrador toEntity(CreateRegistradorDTO dto) {
    if(dto == null) return null;

    Registrador registrador = new Registrador();
    registrador.setNombre(dto.getNombre());
    registrador.setUsuario(dto.getUsuario());
    registrador.setPassword(dto.getPassword());

    return registrador;
  }

  public Registrador toEntity(UpdateRegistradorDTO dto) {
    if(dto == null) return null;

    Registrador registrador = new Registrador();
    registrador.setIdRegistrador(dto.getIdRegistrador());
    registrador.setNombre(dto.getNombre());
    registrador.setUsuario(dto.getUsuario());
    registrador.setPassword(dto.getPassword());

    return registrador;
  }

  public ResponseRegistradorDTO toResponseDTO(Registrador registrador) {
    if(registrador == null) return null;

    ResponseRegistradorDTO dto = new ResponseRegistradorDTO();
    dto.setIdRegistrador(registrador.getIdRegistrador());
    dto.setNombre(registrador.getNombre());
    dto.setUsuario(registrador.getUsuario());

    return dto;
  }

  public List<ResponseRegistradorDTO> toResponseDTOs(List<Registrador> registradores) {
    return registradores
      .stream()
      .map(this::toResponseDTO)
      .toList();
  }
}
