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
  public ResponseCandidatoDTO addCandidato(CreateCandidatoDTO candidatoDTO);
  public void updateCandidatoFoto(Long id, MultipartFile foto);
  public void updateCandidatoFormularioE6(Long id, MultipartFile formularioE6);
  public void updateCandidatoCertificado(Long id, MultipartFile certificado);
  public void updateCandidatoCedula(Long id, MultipartFile cedula);
  public void updateCandidatoAval(Long id, MultipartFile aval);
  public ResponseCandidatoDTO updateCandidato(UpdateCandidatoDTO candidatoDTO);
  public ResponseCandidatoDTO updateCandidato(Long id, UpdateCandidatoDTO candidatoDTO);
  public void deleteById(Long id);
}
