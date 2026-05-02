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

import PortalRegistraduriaBack.dtos.mesa.CreateMesaDTO;
import PortalRegistraduriaBack.dtos.mesa.ResponseMesaDTO;
import PortalRegistraduriaBack.dtos.mesa.UpdateMesaDTO;
import PortalRegistraduriaBack.services.mesa.IServiceMesa;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/mesa")
@Tag(name = "Mesa", description = "Gestión de mesas de votación")
public class ControllerMesa {

  @Autowired
  IServiceMesa serviceMesa;

  @Operation(summary = "Obtener todas las mesas")
  @GetMapping("/mesas")
  public ResponseEntity<List<ResponseMesaDTO>> obtenerMesas() {
    return ResponseEntity.ok(serviceMesa.findAll());
  }

  @Operation(summary = "Obtener mesa por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Mesa encontrada"),
      @ApiResponse(responseCode = "404", description = "Mesa no encontrada")
  })
  @GetMapping("/{idMesa}")
  public ResponseEntity<ResponseMesaDTO> obtenerMesaPorId(@PathVariable Long idMesa) {
    return ResponseEntity.ok(serviceMesa.findById(idMesa));
  }

  @Operation(summary = "Obtener mesas por centro de votación")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Mesas del centro encontradas"),
      @ApiResponse(responseCode = "404", description = "Centro de votación no encontrado")
  })
  @GetMapping("/centro/{idCentroVotacion}")
  public ResponseEntity<List<ResponseMesaDTO>> obtenerMesasPorCentro(
      @PathVariable Long idCentroVotacion) {
    return ResponseEntity.ok(serviceMesa.findByCentroVotacion(idCentroVotacion));
  }

  @Operation(summary = "Crear una nueva mesa")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Mesa creada exitosamente"),
      @ApiResponse(responseCode = "404", description = "Centro de votación no encontrado"),
      @ApiResponse(responseCode = "400", description = "Tipo de mesa inválido")
  })
  @PostMapping("/add")
  public ResponseEntity<ResponseMesaDTO> crearMesa(@RequestBody CreateMesaDTO dto) {
    return ResponseEntity.ok(serviceMesa.addMesa(dto));
  }

  @Operation(summary = "Actualizar mesa por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Mesa actualizada"),
      @ApiResponse(responseCode = "404", description = "Mesa o centro de votación no encontrado"),
      @ApiResponse(responseCode = "400", description = "Tipo de mesa inválido")
  })
  @PutMapping("/{idMesa}")
  public ResponseEntity<ResponseMesaDTO> actualizarMesa(
      @PathVariable Long idMesa,
      @RequestBody UpdateMesaDTO dto) {
    return ResponseEntity.ok(serviceMesa.updateMesa(idMesa, dto));
  }

  @Operation(summary = "Eliminar mesa por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Mesa eliminada"),
      @ApiResponse(responseCode = "404", description = "Mesa no encontrada")
  })
  @DeleteMapping("/{idMesa}")
  public ResponseEntity<Void> eliminarMesa(@PathVariable Long idMesa) {
    serviceMesa.deleteById(idMesa);
    return ResponseEntity.noContent().build();
  }

}
