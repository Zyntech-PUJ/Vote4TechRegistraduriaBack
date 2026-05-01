package PortalRegistraduriaBack.services.consejonacional;

import java.util.List;

import PortalRegistraduriaBack.dtos.consejonacional.CreateConsejoNacionalDTO;
import PortalRegistraduriaBack.dtos.consejonacional.LoginConsejoNacionalDTO;
import PortalRegistraduriaBack.dtos.consejonacional.ResponseConsejoNacionalDTO;
import PortalRegistraduriaBack.dtos.consejonacional.UpdateConsejoNacionalDTO;

public interface IServiceConsejoNacional {

  public List<ResponseConsejoNacionalDTO> findAll();
  public ResponseConsejoNacionalDTO findById(Long id);
  public ResponseConsejoNacionalDTO addConsejoNacional(CreateConsejoNacionalDTO consejoNacionalDTO);
  public ResponseConsejoNacionalDTO updateConsejoNacional(UpdateConsejoNacionalDTO consejoNacionalDTO);
  public ResponseConsejoNacionalDTO updateConsejoNacional(Long id, UpdateConsejoNacionalDTO consejoNacionalDTO);
  public String login(LoginConsejoNacionalDTO loginDTO);
  public void deleteById(Long id);

}
