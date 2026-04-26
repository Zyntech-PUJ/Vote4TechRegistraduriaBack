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

import PortalRegistraduriaBack.dtos.registrador.CreateRegistradorDTO;
import PortalRegistraduriaBack.dtos.registrador.LoginRegistradorDTO;
import PortalRegistraduriaBack.dtos.registrador.ResponseRegistradorDTO;
import PortalRegistraduriaBack.dtos.registrador.UpdateRegistradorDTO;
import PortalRegistraduriaBack.services.registrador.IServiceRegistrador;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/registrador")
@Tag(name = "Registrador", description = "Gestión de registradores")
public class ControllerRegistrador {

  @Autowired
  IServiceRegistrador serviceRegistrador;

  @Operation(summary = "Obtener todos los registradores")
  @GetMapping("/registradores")
  public ResponseEntity<List<ResponseRegistradorDTO>> obtenerRegistradores() {
    List<ResponseRegistradorDTO> responseRegistradoresDTO = serviceRegistrador.findAll();

    return ResponseEntity.ok(responseRegistradoresDTO);
  }

  @Operation(summary = "Obtener registrador por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Registrador encontrado"),
      @ApiResponse(responseCode = "404", description = "Registrador no encontrado")
  })
  @GetMapping("/{idRegistrador}")
  public ResponseEntity<ResponseRegistradorDTO> obtenerRegistradorById(@PathVariable Long idRegistrador) {
    ResponseRegistradorDTO responseRegistradorDTO = serviceRegistrador.findById(idRegistrador);

    return ResponseEntity.ok(responseRegistradorDTO);
  }

  @Operation(summary = "Crear un nuevo registrador")
  @ApiResponse(responseCode = "200", description = "Registrador creado exitosamente")
  @PostMapping("/add")
  public ResponseEntity<ResponseRegistradorDTO> crearRegistrador(@RequestBody CreateRegistradorDTO registradorDTO) {
    ResponseRegistradorDTO responseRegistradorDTO = serviceRegistrador.addRegistrador(registradorDTO);

    return ResponseEntity.ok(responseRegistradorDTO);
  }

  @Operation(summary = "Login de registrador")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Login exitoso"),
      @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
  })
  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginRegistradorDTO loginDTO) {
    return ResponseEntity.ok(serviceRegistrador.login(loginDTO));
  }

  @Operation(summary = "Actualizar registrador por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Registrador actualizado"),
      @ApiResponse(responseCode = "404", description = "Registrador no encontrado")
  })
  @PutMapping("/{idRegistrador}")
  public ResponseEntity<ResponseRegistradorDTO> actualizarRegistrador(
      @PathVariable Long idRegistrador,
      @RequestBody UpdateRegistradorDTO registradorDTO) {
    return ResponseEntity.ok(serviceRegistrador.updateRegistrador(idRegistrador, registradorDTO));
  }

  @Operation(summary = "Eliminar registrador por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Registrador eliminado"),
      @ApiResponse(responseCode = "404", description = "Registrador no encontrado")
  })
  @DeleteMapping("/{idRegistrador}")
  public ResponseEntity<Void> eliminarRegistrador(@PathVariable Long idRegistrador) {
    serviceRegistrador.deleteById(idRegistrador);
    return ResponseEntity.noContent().build();
  }
}
