package PortalRegistraduriaBack.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PortalRegistraduriaBack.dtos.centrovotacion.CreateCentroVotacionDTO;
import PortalRegistraduriaBack.dtos.centrovotacion.ResponseCentroVotacionDTO;
import PortalRegistraduriaBack.dtos.centrovotacion.UpdateCentroVotacionDTO;
import PortalRegistraduriaBack.services.centrovotacion.IServiceCentroVotacion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/centro-votacion")
@Tag(name = "CentroVotacion", description = "Gestión de centros de votación")
public class ControllerCentroVotacion {

  @Autowired
  IServiceCentroVotacion serviceCentroVotacion;

  @Operation(summary = "Obtener todos los centros de votación")
  @GetMapping("/centros")
  public ResponseEntity<List<ResponseCentroVotacionDTO>> obtenerCentros() {
    return ResponseEntity.ok(serviceCentroVotacion.findAll());
  }

  @Operation(summary = "Obtener centro de votación por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Centro de votación encontrado"),
      @ApiResponse(responseCode = "404", description = "Centro de votación no encontrado")
  })
  @GetMapping("/{idCentroVotacion}")
  public ResponseEntity<ResponseCentroVotacionDTO> obtenerCentroPorId(
      @PathVariable Long idCentroVotacion) {
    return ResponseEntity.ok(serviceCentroVotacion.findById(idCentroVotacion));
  }

  @Operation(summary = "Crear un nuevo centro de votación")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Centro de votación creado exitosamente")
  })
  @PostMapping("/add")
  public ResponseEntity<ResponseCentroVotacionDTO> crearCentro(
      @RequestBody CreateCentroVotacionDTO dto) {
    return ResponseEntity.ok(serviceCentroVotacion.addCentroVotacion(dto));
  }

  @Operation(summary = "Actualizar centro de votación por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Centro de votación actualizado"),
      @ApiResponse(responseCode = "404", description = "Centro de votación no encontrado")
  })
  @PutMapping("/{idCentroVotacion}")
  public ResponseEntity<ResponseCentroVotacionDTO> actualizarCentro(
      @PathVariable Long idCentroVotacion,
      @RequestBody UpdateCentroVotacionDTO dto) {
    return ResponseEntity.ok(serviceCentroVotacion.updateCentroVotacion(idCentroVotacion, dto));
  }

  @Operation(summary = "Eliminar centro de votación por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Centro de votación eliminado"),
      @ApiResponse(responseCode = "404", description = "Centro de votación no encontrado")
  })
  @DeleteMapping("/{idCentroVotacion}")
  public ResponseEntity<Void> eliminarCentro(@PathVariable Long idCentroVotacion) {
    serviceCentroVotacion.deleteById(idCentroVotacion);
    return ResponseEntity.noContent().build();
  }

}
