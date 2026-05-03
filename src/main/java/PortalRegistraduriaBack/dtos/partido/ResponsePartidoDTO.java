package PortalRegistraduriaBack.dtos.partido;

import lombok.Data;

@Data
public class ResponsePartidoDTO {

  private Long idPartido;
  private String nombre;
  private String sigla;
  private Long idRegistrador;
  
}
