package PortalRegistraduriaBack.dtos.partido;

import lombok.Data;

@Data
public class CreatePartidoDTO {

  private String nombre;
  private String sigla;
  private Long idRegistrador;
  
}
