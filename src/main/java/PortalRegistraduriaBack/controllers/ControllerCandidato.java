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

import PortalRegistraduriaBack.dtos.candidato.CreateCandidatoDTO;
import PortalRegistraduriaBack.dtos.candidato.ResponseCandidatoDTO;
import PortalRegistraduriaBack.dtos.candidato.UpdateCandidatoDTO;
import PortalRegistraduriaBack.services.candidato.IServiceCandidato;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/candidato")
@Tag(name = "Candidato", description = "Gestión de candidatos")
public class ControllerCandidato {

  @Autowired
  IServiceCandidato serviceCandidato;

  @Operation(summary = "Obtener todos los candidatos")
  @GetMapping("/candidatos")
  public ResponseEntity<List<ResponseCandidatoDTO>> obtenerCandidatos() {
    return ResponseEntity.ok(serviceCandidato.findAll());
  }

  @Operation(summary = "Obtener candidato por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Candidato encontrado"),
      @ApiResponse(responseCode = "404", description = "Candidato no encontrado")
  })
  @GetMapping("/{idCandidato}")
  public ResponseEntity<ResponseCandidatoDTO> obtenerCandidatoById(@PathVariable Long idCandidato) {
    return ResponseEntity.ok(serviceCandidato.findById(idCandidato));
  }

  @Operation(summary = "Crear un nuevo candidato")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Candidato creado exitosamente"),
      @ApiResponse(responseCode = "404", description = "Registrador no encontrado")
  })
  @PostMapping("/add")
  public ResponseEntity<ResponseCandidatoDTO> crearCandidato(@RequestBody CreateCandidatoDTO candidatoDTO) {
    return ResponseEntity.ok(serviceCandidato.addCandidato(candidatoDTO));
  }

  @Operation(summary = "Actualizar candidato por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Candidato actualizado"),
      @ApiResponse(responseCode = "404", description = "Candidato o registrador no encontrado")
  })
  @PutMapping("/{idCandidato}")
  public ResponseEntity<ResponseCandidatoDTO> actualizarCandidato(
      @PathVariable Long idCandidato,
      @RequestBody UpdateCandidatoDTO candidatoDTO) {
    return ResponseEntity.ok(serviceCandidato.updateCandidato(idCandidato, candidatoDTO));
  }

  @Operation(summary = "Eliminar candidato por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Candidato eliminado"),
      @ApiResponse(responseCode = "404", description = "Candidato no encontrado")
  })
  @DeleteMapping("/{idCandidato}")
  public ResponseEntity<Void> eliminarCandidato(@PathVariable Long idCandidato) {
    serviceCandidato.deleteById(idCandidato);
    return ResponseEntity.noContent().build();
  }
}