package PortalRegistraduriaBack.dtos.centrovotacion;

import lombok.Data;

@Data
public class ResponseCentroVotacionDTO {

  private Long idCentroVotacion;
  private String nombre;
  private String direccion;
  private String ciudad;
  private String departamento;
  private Boolean activo;

}
