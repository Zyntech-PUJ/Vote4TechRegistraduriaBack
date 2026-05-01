package PortalRegistraduriaBack.dtos.administradorelectoral;

import java.util.List;

import org.springframework.stereotype.Component;

import PortalRegistraduriaBack.entities.AdministradorElectoral;

@Component
public class MapperAdministradorElectoral {

  public AdministradorElectoral toEntity(CreateAdministradorElectoralDTO dto) {
    if (dto == null) return null;

    AdministradorElectoral administradorElectoral = new AdministradorElectoral();
    administradorElectoral.setUsuario(dto.getUsuario());
    administradorElectoral.setPassword(dto.getPassword());

    return administradorElectoral;
  }

  public AdministradorElectoral toEntity(UpdateAdministradorElectoralDTO dto) {
    if (dto == null) return null;

    AdministradorElectoral administradorElectoral = new AdministradorElectoral();
    administradorElectoral.setIdAdministradorElectoral(dto.getIdAdministradorElectoral());
    administradorElectoral.setUsuario(dto.getUsuario());
    administradorElectoral.setPassword(dto.getPassword());

    return administradorElectoral;
  }

  public ResponseAdministradorElectoralDTO toResponseDTO(AdministradorElectoral administradorElectoral) {
    if (administradorElectoral == null) return null;

    ResponseAdministradorElectoralDTO dto = new ResponseAdministradorElectoralDTO();
    dto.setIdAdministradorElectoral(administradorElectoral.getIdAdministradorElectoral());
    dto.setUsuario(administradorElectoral.getUsuario());

    return dto;
  }

  public List<ResponseAdministradorElectoralDTO> toResponseDTOs(
      List<AdministradorElectoral> administradoresElectorales) {
    return administradoresElectorales
      .stream()
      .map(this::toResponseDTO)
      .toList();
  }
}
