package PortalRegistraduriaBack.services.candidato;

import java.util.List;

import PortalRegistraduriaBack.dtos.candidato.CreateCandidatoDTO;
import PortalRegistraduriaBack.dtos.candidato.ResponseCandidatoDTO;
import PortalRegistraduriaBack.dtos.candidato.UpdateCandidatoDTO;

public interface IServiceCandidato {

  public List<ResponseCandidatoDTO> findAll();
  public ResponseCandidatoDTO findById(Long id);
  public ResponseCandidatoDTO addCandidato(CreateCandidatoDTO candidatoDTO);
  public ResponseCandidatoDTO updateCandidato(UpdateCandidatoDTO candidatoDTO);
  public ResponseCandidatoDTO updateCandidato(Long id, UpdateCandidatoDTO candidatoDTO);
  public void deleteById(Long id);
}