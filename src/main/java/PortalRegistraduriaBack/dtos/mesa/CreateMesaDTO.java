package PortalRegistraduriaBack.dtos.mesa;

import lombok.Data;

@Data
public class CreateMesaDTO {

  private Integer numero;
  private String tipo;
  private Boolean activo;
  private Long idCentroVotacion;

}
