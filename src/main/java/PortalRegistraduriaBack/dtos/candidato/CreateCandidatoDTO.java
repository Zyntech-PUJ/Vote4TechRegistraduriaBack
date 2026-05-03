package PortalRegistraduriaBack.dtos.candidato;

import lombok.Data;

@Data
public class CreateCandidatoDTO {
  
  private String nombre;
  private String numero;
  private Boolean activo;
  private Long idLista;
  private Long idPartido;
  private Long idRegistrador;

}
