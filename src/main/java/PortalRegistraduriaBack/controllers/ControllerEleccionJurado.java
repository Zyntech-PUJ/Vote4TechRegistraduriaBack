package PortalRegistraduriaBack.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import PortalRegistraduriaBack.dtos.eleccionjurado.CreateEleccionJuradoDTO;
import PortalRegistraduriaBack.dtos.eleccionjurado.ResponseEleccionJuradoDTO;
import PortalRegistraduriaBack.services.eleccionjurado.IServiceEleccionJurado;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/eleccion-jurado")
@Tag(name = "Eleccion Jurado", description = "Gestión del sorteo y listado de jurados por elección")
public class ControllerEleccionJurado {

  @Autowired
  IServiceEleccionJurado serviceEleccionJurado;

  @Operation(summary = "Obtener todas las asignaciones de jurados")
  @GetMapping
  public ResponseEntity<List<ResponseEleccionJuradoDTO>> obtenerAsignaciones() {
    return ResponseEntity.ok(serviceEleccionJurado.findAll());
  }

  @Operation(summary = "Listar jurados de una elección")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Jurados consultados correctamente"),
      @ApiResponse(responseCode = "404", description = "Elección no encontrada")
  })
  @GetMapping("/eleccion/{idEleccion}")
  public ResponseEntity<List<ResponseEleccionJuradoDTO>> obtenerJuradosPorEleccion(@PathVariable Long idEleccion) {
    return ResponseEntity.ok(serviceEleccionJurado.findByEleccion(idEleccion));
  }

  @Operation(summary = "Crear jurado manualmente para una elección")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Jurado creado correctamente"),
      @ApiResponse(responseCode = "404", description = "Elección o ciudadano no encontrado"),
      @ApiResponse(responseCode = "409", description = "El ciudadano ya fue asignado como jurado en esa elección")
  })
  @PostMapping("/eleccion/{idEleccion}")
  public ResponseEntity<ResponseEleccionJuradoDTO> crearJurado(
      @PathVariable Long idEleccion,
      @RequestBody CreateEleccionJuradoDTO eleccionJuradoDTO) {
    return ResponseEntity.ok(serviceEleccionJurado.addEleccionJurado(idEleccion, eleccionJuradoDTO));
  }

  @Operation(summary = "Generar sorteo de jurados para una elección")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Sorteo generado correctamente"),
      @ApiResponse(responseCode = "404", description = "Elección no encontrada"),
      @ApiResponse(responseCode = "409", description = "La elección ya tenía jurados sorteados o no hay ciudadanos disponibles")
  })
  @PostMapping("/eleccion/{idEleccion}/sortear")
  public ResponseEntity<List<ResponseEleccionJuradoDTO>> generarSorteo(@PathVariable Long idEleccion) {
    return ResponseEntity.ok(serviceEleccionJurado.generarSorteo(idEleccion));
  }
}
