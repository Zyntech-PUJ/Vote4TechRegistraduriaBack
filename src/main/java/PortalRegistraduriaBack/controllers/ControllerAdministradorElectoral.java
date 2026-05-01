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

import PortalRegistraduriaBack.dtos.administradorelectoral.CreateAdministradorElectoralDTO;
import PortalRegistraduriaBack.dtos.administradorelectoral.LoginAdministradorElectoralDTO;
import PortalRegistraduriaBack.dtos.administradorelectoral.ResponseAdministradorElectoralDTO;
import PortalRegistraduriaBack.dtos.administradorelectoral.UpdateAdministradorElectoralDTO;
import PortalRegistraduriaBack.services.administradorelectoral.IServiceAdministradorElectoral;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/administrador-electoral")
@Tag(name = "Administrador Electoral", description = "Gestión de administradores electorales")
public class ControllerAdministradorElectoral {

  @Autowired
  private IServiceAdministradorElectoral serviceAdministradorElectoral;

  @Operation(summary = "Obtener todos los administradores electorales")
  @GetMapping("/administradores")
  public ResponseEntity<List<ResponseAdministradorElectoralDTO>> obtenerAdministradoresElectorales() {
    return ResponseEntity.ok(serviceAdministradorElectoral.findAll());
  }

  @Operation(summary = "Obtener administrador electoral por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Administrador electoral encontrado"),
      @ApiResponse(responseCode = "404", description = "Administrador electoral no encontrado")
  })
  @GetMapping("/{idAdministradorElectoral}")
  public ResponseEntity<ResponseAdministradorElectoralDTO> obtenerAdministradorElectoralById(
      @PathVariable Long idAdministradorElectoral) {
    return ResponseEntity.ok(serviceAdministradorElectoral.findById(idAdministradorElectoral));
  }

  @Operation(summary = "Crear un nuevo administrador electoral")
  @ApiResponse(responseCode = "200", description = "Administrador electoral creado exitosamente")
  @PostMapping("/add")
  public ResponseEntity<ResponseAdministradorElectoralDTO> crearAdministradorElectoral(
      @RequestBody CreateAdministradorElectoralDTO administradorElectoralDTO) {
    return ResponseEntity.ok(
        serviceAdministradorElectoral.addAdministradorElectoral(administradorElectoralDTO));
  }

  @Operation(summary = "Login de administrador electoral")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Login exitoso"),
      @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
  })
  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginAdministradorElectoralDTO loginDTO) {
    return ResponseEntity.ok(serviceAdministradorElectoral.login(loginDTO));
  }

  @Operation(summary = "Actualizar administrador electoral por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Administrador electoral actualizado"),
      @ApiResponse(responseCode = "404", description = "Administrador electoral no encontrado")
  })
  @PutMapping("/{idAdministradorElectoral}")
  public ResponseEntity<ResponseAdministradorElectoralDTO> actualizarAdministradorElectoral(
      @PathVariable Long idAdministradorElectoral,
      @RequestBody UpdateAdministradorElectoralDTO administradorElectoralDTO) {
    return ResponseEntity.ok(
        serviceAdministradorElectoral.updateAdministradorElectoral(
            idAdministradorElectoral,
            administradorElectoralDTO));
  }

  @Operation(summary = "Eliminar administrador electoral por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Administrador electoral eliminado"),
      @ApiResponse(responseCode = "404", description = "Administrador electoral no encontrado")
  })
  @DeleteMapping("/{idAdministradorElectoral}")
  public ResponseEntity<Void> eliminarAdministradorElectoral(@PathVariable Long idAdministradorElectoral) {
    serviceAdministradorElectoral.deleteById(idAdministradorElectoral);
    return ResponseEntity.noContent().build();
  }
}
