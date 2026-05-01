package PortalRegistraduriaBack.dtos.consejonacional;

import java.util.List;

import org.springframework.stereotype.Component;

import PortalRegistraduriaBack.entities.ConsejoNacional;

@Component
public class MapperConsejoNacional {

  public ConsejoNacional toEntity(CreateConsejoNacionalDTO dto) {
    if (dto == null) return null;

    ConsejoNacional consejoNacional = new ConsejoNacional();
    consejoNacional.setUsername(dto.getUsername());
    consejoNacional.setPassword(dto.getPassword());

    return consejoNacional;
  }

  public ConsejoNacional toEntity(UpdateConsejoNacionalDTO dto) {
    if (dto == null) return null;

    ConsejoNacional consejoNacional = new ConsejoNacional();
    consejoNacional.setIdConsejoNacional(dto.getIdConsejoNacional());
    consejoNacional.setUsername(dto.getUsername());
    consejoNacional.setPassword(dto.getPassword());

    return consejoNacional;
  }

  public ResponseConsejoNacionalDTO toResponseDTO(ConsejoNacional consejoNacional) {
    if (consejoNacional == null) return null;

    ResponseConsejoNacionalDTO dto = new ResponseConsejoNacionalDTO();
    dto.setIdConsejoNacional(consejoNacional.getIdConsejoNacional());
    dto.setUsername(consejoNacional.getUsername());

    return dto;
  }

  public List<ResponseConsejoNacionalDTO> toResponseDTOs(List<ConsejoNacional> consejosNacionales) {
    return consejosNacionales
      .stream()
      .map(this::toResponseDTO)
      .toList();
  }
}
