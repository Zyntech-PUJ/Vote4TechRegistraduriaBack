package PortalRegistraduriaBack.services.partido;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import PortalRegistraduriaBack.dtos.partido.CreatePartidoDTO;
import PortalRegistraduriaBack.dtos.partido.ResponsePartidoDTO;

public interface IServicePartido {
  
  public List<ResponsePartidoDTO> findAll();
  public ResponsePartidoDTO findById(Long id);
  public ResponsePartidoDTO addPartido(
    CreatePartidoDTO partidoDTO,
    MultipartFile logo,
    MultipartFile estatutos,
    MultipartFile plataforma,
    MultipartFile registro,
    MultipartFile certificado
  );
  public void deleteById(Long id);
  
}
