package PortalRegistraduriaBack.dtos.mesa;

import lombok.Data;

@Data
public class UpdateMesaDTO {

  private Integer numero;
  private String tipo;
  private Boolean activo;
  private Long idCentroVotacion;

}
