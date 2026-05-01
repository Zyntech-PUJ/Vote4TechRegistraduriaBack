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

import PortalRegistraduriaBack.dtos.consejonacional.CreateConsejoNacionalDTO;
import PortalRegistraduriaBack.dtos.consejonacional.LoginConsejoNacionalDTO;
import PortalRegistraduriaBack.dtos.consejonacional.ResponseConsejoNacionalDTO;
import PortalRegistraduriaBack.dtos.consejonacional.UpdateConsejoNacionalDTO;
import PortalRegistraduriaBack.services.consejonacional.IServiceConsejoNacional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/consejo-nacional")
@Tag(name = "Consejo Nacional Electoral", description = "Gestión del consejo nacional electoral")
public class ControllerConsejoNacional {

  @Autowired
  private IServiceConsejoNacional serviceConsejoNacional;

  @Operation(summary = "Obtener todos los consejos nacionales")
  @GetMapping("/consejos")
  public ResponseEntity<List<ResponseConsejoNacionalDTO>> obtenerConsejosNacionales() {
    return ResponseEntity.ok(serviceConsejoNacional.findAll());
  }

  @Operation(summary = "Obtener consejo nacional por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Consejo nacional encontrado"),
      @ApiResponse(responseCode = "404", description = "Consejo nacional no encontrado")
  })
  @GetMapping("/{idConsejoNacional}")
  public ResponseEntity<ResponseConsejoNacionalDTO> obtenerConsejoNacionalById(
      @PathVariable Long idConsejoNacional) {
    return ResponseEntity.ok(serviceConsejoNacional.findById(idConsejoNacional));
  }

  @Operation(summary = "Crear un nuevo consejo nacional")
  @ApiResponse(responseCode = "200", description = "Consejo nacional creado exitosamente")
  @PostMapping("/add")
  public ResponseEntity<ResponseConsejoNacionalDTO> crearConsejoNacional(
      @RequestBody CreateConsejoNacionalDTO consejoNacionalDTO) {
    return ResponseEntity.ok(serviceConsejoNacional.addConsejoNacional(consejoNacionalDTO));
  }

  @Operation(summary = "Login de consejo nacional")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Login exitoso"),
      @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
  })
  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginConsejoNacionalDTO loginDTO) {
    return ResponseEntity.ok(serviceConsejoNacional.login(loginDTO));
  }

  @Operation(summary = "Actualizar consejo nacional por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Consejo nacional actualizado"),
      @ApiResponse(responseCode = "404", description = "Consejo nacional no encontrado")
  })
  @PutMapping("/{idConsejoNacional}")
  public ResponseEntity<ResponseConsejoNacionalDTO> actualizarConsejoNacional(
      @PathVariable Long idConsejoNacional,
      @RequestBody UpdateConsejoNacionalDTO consejoNacionalDTO) {
    return ResponseEntity.ok(
        serviceConsejoNacional.updateConsejoNacional(idConsejoNacional, consejoNacionalDTO));
  }

  @Operation(summary = "Eliminar consejo nacional por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Consejo nacional eliminado"),
      @ApiResponse(responseCode = "404", description = "Consejo nacional no encontrado")
  })
  @DeleteMapping("/{idConsejoNacional}")
  public ResponseEntity<Void> eliminarConsejoNacional(@PathVariable Long idConsejoNacional) {
    serviceConsejoNacional.deleteById(idConsejoNacional);
    return ResponseEntity.noContent().build();
  }
}
