package PortalRegistraduriaBack.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PortalRegistraduriaBack.dtos.eleccion.CreateEleccionDTO;
import PortalRegistraduriaBack.dtos.eleccion.ResponseEleccionDTO;
import PortalRegistraduriaBack.dtos.eleccion.UpdateEleccionDTO;
import PortalRegistraduriaBack.services.eleccion.IServiceEleccion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/eleccion")
@Tag(name = "Eleccion", description = "Gestión de elecciones y su ciclo de vida")
public class ControllerEleccion {

  @Autowired
  IServiceEleccion serviceEleccion;

  @Operation(summary = "Obtener todas las elecciones")
  @GetMapping("/elecciones")
  public ResponseEntity<List<ResponseEleccionDTO>> obtenerElecciones() {
    return ResponseEntity.ok(serviceEleccion.findAll());
  }

  @Operation(summary = "Obtener elección por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Elección encontrada"),
      @ApiResponse(responseCode = "404", description = "Elección no encontrada")
  })
  @GetMapping("/{idEleccion}")
  public ResponseEntity<ResponseEleccionDTO> obtenerEleccionById(@PathVariable Long idEleccion) {
    return ResponseEntity.ok(serviceEleccion.findById(idEleccion));
  }

  @Operation(summary = "Crear una nueva elección")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Elección creada en estado CONFIGURACION"),
      @ApiResponse(responseCode = "400", description = "Registrador no encontrado")
  })
  @PostMapping("/add")
  public ResponseEntity<ResponseEleccionDTO> crearEleccion(@RequestBody CreateEleccionDTO eleccionDTO) {
    return ResponseEntity.ok(serviceEleccion.addEleccion(eleccionDTO));
  }

  @Operation(summary = "Actualizar elección por ID", description = "Solo permitido en estado CONFIGURACION")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Elección actualizada"),
      @ApiResponse(responseCode = "404", description = "Elección o registrador no encontrado"),
      @ApiResponse(responseCode = "409", description = "La elección no está en estado CONFIGURACION")
  })
  @PutMapping("/{idEleccion}")
  public ResponseEntity<ResponseEleccionDTO> actualizarEleccion(
      @PathVariable Long idEleccion,
      @RequestBody UpdateEleccionDTO eleccionDTO) {
    return ResponseEntity.ok(serviceEleccion.updateEleccion(idEleccion, eleccionDTO));
  }

  @Operation(summary = "Lanzar elección", description = "Cambia el estado de CONFIGURACION a LANZADA")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Elección lanzada exitosamente"),
      @ApiResponse(responseCode = "404", description = "Elección no encontrada"),
      @ApiResponse(responseCode = "409", description = "La elección no está en estado CONFIGURACION")
  })
  @PatchMapping("/{idEleccion}/lanzar")
  public ResponseEntity<ResponseEleccionDTO> lanzarEleccion(@PathVariable Long idEleccion) {
    return ResponseEntity.ok(serviceEleccion.lanzarEleccion(idEleccion));
  }

  @Operation(summary = "Finalizar elección", description = "Cambia el estado de EN_CURSO a FINALIZADA")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Elección finalizada exitosamente"),
      @ApiResponse(responseCode = "404", description = "Elección no encontrada"),
      @ApiResponse(responseCode = "409", description = "La elección no está en estado EN_CURSO")
  })
  @PatchMapping("/{idEleccion}/finalizar")
  public ResponseEntity<ResponseEleccionDTO> finalizarEleccion(@PathVariable Long idEleccion) {
    return ResponseEntity.ok(serviceEleccion.finalizarEleccion(idEleccion));
  }

  @Operation(summary = "Eliminar elección por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Elección eliminada"),
      @ApiResponse(responseCode = "404", description = "Elección no encontrada")
  })
  @DeleteMapping("/{idEleccion}")
  public ResponseEntity<Void> eliminarEleccion(@PathVariable Long idEleccion) {
    serviceEleccion.deleteById(idEleccion);
    return ResponseEntity.noContent().build();
  }
}
