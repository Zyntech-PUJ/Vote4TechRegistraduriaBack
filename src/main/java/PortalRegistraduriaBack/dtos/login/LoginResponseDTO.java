package PortalRegistraduriaBack.dtos.login;

import java.util.List;

public record LoginResponseDTO(
  String token,
  String usuario,
  List<String> roles
) { }
