package PortalRegistraduriaBack.dtos.mesa;

import lombok.Data;

@Data
public class ResponseMesaDTO {

  private Long idMesa;
  private Integer numero;
  private String tipo;
  private Boolean activo;
  private Long idCentroVotacion;
  private String nombreCentroVotacion;
  private String ciudadCentroVotacion;
  private String departamentoCentroVotacion;

}
