package PortalRegistraduriaBack.services.partido;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import PortalRegistraduriaBack.dtos.partido.CreatePartidoDTO;
import PortalRegistraduriaBack.dtos.partido.ResponsePartidoDTO;
import PortalRegistraduriaBack.dtos.partido.UpdatePartidoDTO;

public interface IServicePartido {
  
  public List<ResponsePartidoDTO> findAll();
  public ResponsePartidoDTO findById(Long id);
  public byte[] findLogoById(Long id);
  public byte[] findEstatutosById(Long id);
  public byte[] findPlataformaIdeologicaById(Long id);
  public byte[] findRegistroAfiliadosDirectivosById(Long id);
  public byte[] findCertificadoRepresentatividadById(Long id);
  public ResponsePartidoDTO addPartido(CreatePartidoDTO partidoDTO);
  public Boolean updatePartidoLogo(Long id, MultipartFile logo);
  public Boolean updatePartidoEstatutos(Long id, MultipartFile estatutos);
  public Boolean updatePartidoPlataforma(Long id, MultipartFile plataforma);
  public Boolean updatePartidoRegistro(Long id, MultipartFile registro);
  public Boolean updatePartidoCertificado(Long id, MultipartFile certificado);
  public ResponsePartidoDTO updatePartido(UpdatePartidoDTO partidoDTO);
  public ResponsePartidoDTO updatePartido(Long id, UpdatePartidoDTO partidoDTO);

  public void deleteById(Long id);
  
}
