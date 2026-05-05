package PortalRegistraduriaBack.dtos.eleccion;

import java.time.LocalDateTime;

import PortalRegistraduriaBack.dtos.administradorelectoral.ResponseAdministradorElectoralDTO;
import PortalRegistraduriaBack.enums.EstadoEleccion;
import PortalRegistraduriaBack.enums.TipoEleccion;
import lombok.Data;

@Data
public class ResponseEleccionDTO {

  private Long idEleccion;
  private String nombre;
  private LocalDateTime fechaInicio;
  private LocalDateTime fechaFinalizacion;
  private LocalDateTime fechaInicioUrna;
  private LocalDateTime fechaFinalizacionUrna;
  private LocalDateTime fechaInicioDomicilio;
  private LocalDateTime fechaFinalizacionDomicilio;
  private LocalDateTime fechaCreacion;
  private TipoEleccion tipo;
  private Boolean listaAbierta;
  private EstadoEleccion estado;
  private ResponseAdministradorElectoralDTO administradorElectoral; 

}
