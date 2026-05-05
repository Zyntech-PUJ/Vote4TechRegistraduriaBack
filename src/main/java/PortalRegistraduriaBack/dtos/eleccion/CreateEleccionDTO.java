package PortalRegistraduriaBack.dtos.eleccion;

import java.time.LocalDateTime;

import PortalRegistraduriaBack.enums.TipoEleccion;
import lombok.Data;

@Data
public class CreateEleccionDTO {

  private String nombre;
  private LocalDateTime fechaInicio;
  private LocalDateTime fechaFinalizacion;
  private LocalDateTime fechaInicioUrna;
  private LocalDateTime fechaFinalizacionUrna;
  private LocalDateTime fechaInicioDomicilio;
  private LocalDateTime fechaFinalizacionDomicilio;
  private TipoEleccion tipo;
  private Boolean listaAbierta;
  private Long idAdministradorElectoral;  

}
