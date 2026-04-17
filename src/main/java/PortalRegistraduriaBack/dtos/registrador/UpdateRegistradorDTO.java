package PortalRegistraduriaBack.dtos.registrador;

import lombok.Data;

@Data
public class UpdateRegistradorDTO {
  
  private Long idRegistrador;
  private String nombre;
  private String usuario;
  private String password;

}
