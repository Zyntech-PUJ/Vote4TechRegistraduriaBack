package PortalRegistraduriaBack.services.partido;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import PortalRegistraduriaBack.dtos.partido.CreatePartidoDTO;
import PortalRegistraduriaBack.dtos.partido.ResponsePartidoDTO;
import PortalRegistraduriaBack.dtos.partido.UpdatePartidoDTO;

public interface IServicePartido {
  
  public List<ResponsePartidoDTO> findAll();
  public ResponsePartidoDTO findById(Long id);
  byte[] findLogoById(Long id);
  byte[] findEstatutosById(Long id);
  byte[] findPlataformaIdeologicaById(Long id);
  byte[] findRegistroAfiliadosDirectivosById(Long id);
  byte[] findCertificadoRepresentatividadById(Long id);
  public ResponsePartidoDTO addPartido(
    CreatePartidoDTO partidoDTO,
    MultipartFile logo,
    MultipartFile estatutos,
    MultipartFile plataforma,
    MultipartFile registro,
    MultipartFile certificado
  );
  public ResponsePartidoDTO updatePartido(UpdatePartidoDTO partidoDTO);
  public ResponsePartidoDTO updatePartido(Long id, UpdatePartidoDTO partidoDTO);

  public void deleteById(Long id);
  
}
