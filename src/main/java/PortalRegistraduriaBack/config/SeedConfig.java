package PortalRegistraduriaBack.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import PortalRegistraduriaBack.entities.Ciudadano;
import PortalRegistraduriaBack.repositories.RepositoryCiudadano;

@Configuration
public class SeedConfig {

  @Bean
  public CommandLineRunner seedCiudadanos(RepositoryCiudadano repositoryCiudadano) {
    return args -> {
      if (repositoryCiudadano.count() > 0) return; // evita duplicados al reiniciar

      repositoryCiudadano.saveAll(java.util.List.of(
        build("Carlos Andrés Ríos Herrera",    "1005432100", "M", true),
        build("Laura Milena Gómez Peña",       "1032456789", "F", false),
        build("Juan David Martínez López",     "1018293847", "M", true),
        build("Valentina Ruiz Castillo",       "1057384920", "F", true),
        build("Andrés Felipe Vargas Torres",   "1094837261", "M", false),
        build("María José Moreno Salcedo",     "1012938475", "F", true),
        build("Santiago Gómez Ramírez",        "1045678923", "M", true),
        build("Daniela Fernández Castro",      "1067891234", "F", false),
        build("Sebastián Ortega Muñoz",        "1023456780", "M", true),
        build("Camila Díaz Quintero",          "1089012345", "F", true),
        build("Julián Pérez Aguilar",          "1011234567", "M", false),
        build("Isabella Soto Medina",          "1078901234", "F", true),
        build("Nicolás Herrera Jiménez",       "1034567890", "M", true),
        build("Sofía Castellanos Ávila",       "1056789012", "F", false),
        build("Felipe Mendoza Restrepo",       "1000123456", "M", true),
        build("Manuela Rojas Ospina",          "1023459876", "F", true),
        build("David Alejandro Cruz Patiño",   "1098765432", "M", false),
        build("Natalia Vergara Londoño",       "1043217654", "F", true),
        build("Luis Miguel Cárdenas Suárez",   "1065432198", "M", true),
        build("Paola Andrea Beltrán Niño",     "1087654321", "F", false)
      ));

      System.out.println("✅ Seed de ciudadanos cargado correctamente.");
    };
  }

  private Ciudadano build(String nombre, String cedula, String genero, boolean votoObligatorio) {
    return Ciudadano.builder()
      .nombre(nombre)
      .cedula(cedula)
      .genero(genero)
      .votoObligatorio(votoObligatorio)
      .build();
  }
}