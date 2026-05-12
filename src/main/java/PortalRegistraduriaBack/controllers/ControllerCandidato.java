package PortalRegistraduriaBack.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import PortalRegistraduriaBack.dtos.candidato.CreateCandidatoDTO;
import PortalRegistraduriaBack.dtos.candidato.ResponseCandidatoDTO;
import PortalRegistraduriaBack.dtos.candidato.UpdateCandidatoDTO;
import PortalRegistraduriaBack.services.candidato.IServiceCandidato;
import io.swagger.v3.oas.annotations.Operation;
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
  @GetMapping("/{idCandidato}")
  public ResponseEntity<ResponseCandidatoDTO> obtenerCandidatoById(@PathVariable Long idCandidato) {
    return ResponseEntity.ok(serviceCandidato.findById(idCandidato));
  }

  // FOTO - es imagen, no PDF
  @Operation(summary = "Obtener foto del candidato por ID")
  @GetMapping(value = "/{idCandidato}/foto", produces = MediaType.APPLICATION_PDF_VALUE)
  public ResponseEntity<byte[]> obtenerFotoById(@PathVariable Long idCandidato) {
    byte[] foto = serviceCandidato.findFotoById(idCandidato);
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_PDF)
        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"foto.pdf\"")
        .body(foto);
  }

  // FORMULARIO E6 - PDF
  @Operation(summary = "Obtener formulario E6 del candidato por ID")
  @GetMapping(value = "/{idCandidato}/formulario-e6", produces = MediaType.APPLICATION_PDF_VALUE)
  public ResponseEntity<byte[]> obtenerFormularioE6ById(@PathVariable Long idCandidato) {
    byte[] pdf = serviceCandidato.findFormularioE6ById(idCandidato);
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_PDF)
        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"formulario_e6.pdf\"")
        .body(pdf);
  }

  // CERTIFICADO - PDF
  @Operation(summary = "Obtener certificado del candidato por ID")
  @GetMapping(value = "/{idCandidato}/certificado", produces = MediaType.APPLICATION_PDF_VALUE)
  public ResponseEntity<byte[]> obtenerCertificadoById(@PathVariable Long idCandidato) {
    byte[] pdf = serviceCandidato.findCertificadoConsejoEstadoById(idCandidato);
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_PDF)
        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"certificado.pdf\"")
        .body(pdf);
  }

  // CÉDULA - PDF
  @Operation(summary = "Obtener copia de cédula del candidato por ID")
  @GetMapping(value = "/{idCandidato}/cedula", produces = MediaType.APPLICATION_PDF_VALUE)
  public ResponseEntity<byte[]> obtenerCopiaCedulaById(@PathVariable Long idCandidato) {
    byte[] pdf = serviceCandidato.findCopiaCedulaById(idCandidato);
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_PDF)
        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"cedula.pdf\"")
        .body(pdf);
  }

  // AVAL - PDF
  @Operation(summary = "Obtener documento aval del candidato por ID")
  @GetMapping(value = "/{idCandidato}/aval", produces = MediaType.APPLICATION_PDF_VALUE)
  public ResponseEntity<byte[]> obtenerDocumentoAvalById(@PathVariable Long idCandidato) {
    byte[] pdf = serviceCandidato.findDocumentoAvalById(idCandidato);
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_PDF)
        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"aval.pdf\"")
        .body(pdf);
  }

  @Operation(summary = "Crear un nuevo candidato")
  @PostMapping(value = "/add")
  public ResponseEntity<ResponseCandidatoDTO> crearCandidato(@RequestBody CreateCandidatoDTO candidatoDTO) {
    return ResponseEntity.ok(serviceCandidato.addCandidato(candidatoDTO));
  }

  @Operation(summary = "Actualizar candidato por ID")
  @PutMapping("/{idCandidato}")
  public ResponseEntity<ResponseCandidatoDTO> actualizarCandidato(
      @PathVariable Long idCandidato,
      @RequestBody UpdateCandidatoDTO candidatoDTO) {
    return ResponseEntity.ok(serviceCandidato.updateCandidato(idCandidato, candidatoDTO));
  }

  @Operation(summary = "Actualizar foto del candidato por ID")
  @PatchMapping(value = "/{idCandidato}/foto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Void> actualizarFoto(
      @PathVariable Long idCandidato,
      @RequestPart("foto") MultipartFile foto) {
    serviceCandidato.updateCandidatoFoto(idCandidato, foto);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Actualizar formulario E6 del candidato por ID")
  @PatchMapping(value = "/{idCandidato}/formulario-e6", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Void> actualizarFormularioE6(
      @PathVariable Long idCandidato,
      @RequestPart("formularioE6") MultipartFile formularioE6) {
    serviceCandidato.updateCandidatoFormularioE6(idCandidato, formularioE6);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Actualizar certificado del candidato por ID")
  @PatchMapping(value = "/{idCandidato}/certificado", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Void> actualizarCertificado(
      @PathVariable Long idCandidato,
      @RequestPart("certificado") MultipartFile certificado) {
    serviceCandidato.updateCandidatoCertificado(idCandidato, certificado);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Actualizar copia de cédula del candidato por ID")
  @PatchMapping(value = "/{idCandidato}/cedula", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Void> actualizarCedula(
      @PathVariable Long idCandidato,
      @RequestPart("cedula") MultipartFile cedula) {
    serviceCandidato.updateCandidatoCedula(idCandidato, cedula);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Actualizar documento aval del candidato por ID")
  @PatchMapping(value = "/{idCandidato}/aval", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Void> actualizarAval(
      @PathVariable Long idCandidato,
      @RequestPart("aval") MultipartFile aval) {
    serviceCandidato.updateCandidatoAval(idCandidato, aval);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Eliminar candidato por ID")
  @DeleteMapping("/{idCandidato}")
  public ResponseEntity<Void> eliminarCandidato(@PathVariable Long idCandidato) {
    serviceCandidato.deleteById(idCandidato);
    return ResponseEntity.noContent().build();
  }
}
