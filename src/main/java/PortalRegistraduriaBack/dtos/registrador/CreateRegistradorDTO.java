package PortalRegistraduriaBack.dtos.registrador;

import lombok.Data;

@Data
public class CreateRegistradorDTO {

  private String nombre;
  private String usuario;
  private String password;

}
