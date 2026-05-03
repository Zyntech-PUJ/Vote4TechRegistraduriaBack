package PortalRegistraduriaBack.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import PortalRegistraduriaBack.dtos.partido.CreatePartidoDTO;
import PortalRegistraduriaBack.dtos.partido.ResponsePartidoDTO;
import PortalRegistraduriaBack.services.partido.IServicePartido;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/partido")
@Tag(name = "Partido", description = "Gestion de partidos y su ciclo de vida")
public class ControllerPartido {
  
  @Autowired
  IServicePartido servicePartido;

  @Operation(summary = "Obtener todos los partidos")
  @GetMapping("/partidos")
  public ResponseEntity<List<ResponsePartidoDTO>> obtenerPartidos() {
    return ResponseEntity.ok(servicePartido.findAll());
  }

  @Operation(summary = "Obtener partido por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Partido encontrado"),
      @ApiResponse(responseCode = "404", description = "Partido no encontrado")
  })
  @GetMapping("/{idPartido}")
  public ResponseEntity<ResponsePartidoDTO> obtenerPartidoById(@PathVariable Long idPartido) {
    return ResponseEntity.ok(servicePartido.findById(idPartido));
  }

  @Operation(summary = "Crear un nuevo partido")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Partido creado exitosamente"),
      @ApiResponse(responseCode = "404", description = "Registrador no encontrado")
  })
  @PostMapping(value = "/add", consumes = "multipart/form-data")
  public ResponseEntity<ResponsePartidoDTO> crearPartido(
    @RequestPart("data") CreatePartidoDTO partidoDTO,
    @RequestPart("logo") MultipartFile logo,
    @RequestPart("estatutos") MultipartFile estatutos,
    @RequestPart("plataforma") MultipartFile plataforma,
    @RequestPart("registro") MultipartFile registro,
    @RequestPart("certificado") MultipartFile certificado
  ) {
    return ResponseEntity.ok(
      servicePartido.addPartido(
        partidoDTO,
        logo,
        estatutos,
        plataforma,
        registro,
        certificado
      )
    );
  }

  @Operation(summary = "Eliminar partido por ID")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "Partido eliminado"),
      @ApiResponse(responseCode = "404", description = "Partido no encontrado")
  })
  @DeleteMapping("/{idPartido}")
  public ResponseEntity<Void> eliminarPartido(@PathVariable Long idPartido) {
    servicePartido.deleteById(idPartido);
    return ResponseEntity.noContent().build();
  }
}
