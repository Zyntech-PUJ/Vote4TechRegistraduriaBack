package PortalRegistraduriaBack.dtos.partido;

import lombok.Data;

@Data
public class UpdatePartidoDTO {

  private Long idPartido;
  private String nombre;
  private String sigla;
  private Boolean activo;
  private Long idRegistrador;

}
