package PortalRegistraduriaBack.services.candidato;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import PortalRegistraduriaBack.dtos.candidato.CreateCandidatoDTO;
import PortalRegistraduriaBack.dtos.candidato.ResponseCandidatoDTO;
import PortalRegistraduriaBack.dtos.candidato.UpdateCandidatoDTO;

public interface IServiceCandidato {

  public List<ResponseCandidatoDTO> findAll();
  public ResponseCandidatoDTO findById(Long id);
  byte[] findFotoById(Long id);
  byte[] findFormularioE6ById(Long id);
  byte[] findCertificadoConsejoEstadoById(Long id);
  byte[] findCopiaCedulaById(Long id);
  byte[] findDocumentoAvalById(Long id);
  public ResponseCandidatoDTO addCandidato(
    CreateCandidatoDTO candidatoDTO,
    MultipartFile foto,
    MultipartFile formularioE6,
    MultipartFile certificado,
    MultipartFile cedula,
    MultipartFile aval
  );
  public ResponseCandidatoDTO updateCandidato(UpdateCandidatoDTO candidatoDTO);
  public ResponseCandidatoDTO updateCandidato(Long id, UpdateCandidatoDTO candidatoDTO);
  public void deleteById(Long id);
}
