package PortalRegistraduriaBack.dtos.eleccion;

import java.time.LocalDateTime;

import PortalRegistraduriaBack.enums.TipoEleccion;
import lombok.Data;

@Data
public class UpdateEleccionDTO {
  
  private Long idEleccion;
  private String nombre;
  private LocalDateTime fechaInicio;
  private LocalDateTime fechaFinalizacion;
  private TipoEleccion tipo;
  private Boolean listaAbierta;
  private Long idAdministradorElectoral;  

}
