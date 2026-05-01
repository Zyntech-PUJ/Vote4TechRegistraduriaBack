package PortalRegistraduriaBack.dtos.administradorelectoral;

import lombok.Data;

@Data
public class UpdateAdministradorElectoralDTO {

  private Long idAdministradorElectoral;
  private String usuario;
  private String password;

}
