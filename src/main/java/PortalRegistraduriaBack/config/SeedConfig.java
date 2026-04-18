package PortalRegistraduriaBack.config;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import PortalRegistraduriaBack.entities.Candidato;
import PortalRegistraduriaBack.entities.Ciudadano;
import PortalRegistraduriaBack.entities.Eleccion;
import PortalRegistraduriaBack.entities.EleccionJurado;
import PortalRegistraduriaBack.entities.Registrador;
import PortalRegistraduriaBack.enums.EstadoEleccion;
import PortalRegistraduriaBack.enums.TipoJurado;
import PortalRegistraduriaBack.repositories.RepositoryCandidato;
import PortalRegistraduriaBack.repositories.RepositoryCiudadano;
import PortalRegistraduriaBack.repositories.RepositoryEleccion;
import PortalRegistraduriaBack.repositories.RepositoryEleccionJurado;
import PortalRegistraduriaBack.repositories.RepositoryRegistrador;

@Configuration
public class SeedConfig {

  @Bean
  public CommandLineRunner seed(
      RepositoryRegistrador repositoryRegistrador,
      RepositoryEleccion repositoryEleccion,
      RepositoryCiudadano repositoryCiudadano,
      RepositoryCandidato repositoryCandidato,
      RepositoryEleccionJurado repositoryEleccionJurado) {

    return args -> {
      // Evita duplicados al reiniciar
      if (repositoryRegistrador.count() > 0) return;

      // ── 1. REGISTRADORES ─────────────────────────────────────────────
      Registrador r1 = repositoryRegistrador.save(Registrador.builder()
          .nombre("Ana Patricia Suárez").usuario("asuarez")
          .password("$2a$10$dummyhash1").build());

      Registrador r2 = repositoryRegistrador.save(Registrador.builder()
          .nombre("Jorge Iván Medina").usuario("jmedina")
          .password("$2a$10$dummyhash2").build());

      Registrador r3 = repositoryRegistrador.save(Registrador.builder()
          .nombre("Lucía Fernanda Ríos").usuario("lrios")
          .password("$2a$10$dummyhash3").build());

      Registrador r4 = repositoryRegistrador.save(Registrador.builder()
          .nombre("Camilo Ernesto Vega").usuario("cvega")
          .password("$2a$10$dummyhash4").build());

      System.out.println("✅ Registradores cargados.");

      // ── 2. ELECCIONES ────────────────────────────────────────────────
      Eleccion e1 = repositoryEleccion.save(Eleccion.builder()
          .nombre("Elecciones Presidenciales 2026")
          .tipo("PRESIDENCIAL")
          .fechaInicio(LocalDateTime.of(2026, 5, 25, 8, 0))
          .fechaFinalizacion(LocalDateTime.of(2026, 5, 25, 16, 0))
          .fechaCreacion(LocalDateTime.now())
          .listaAbierta(false)
          .estado(EstadoEleccion.CONFIGURACION)
          .registrador(r1).build());

      Eleccion e2 = repositoryEleccion.save(Eleccion.builder()
          .nombre("Elecciones Legislativas 2026")
          .tipo("LEGISLATIVA")
          .fechaInicio(LocalDateTime.of(2026, 3, 13, 8, 0))
          .fechaFinalizacion(LocalDateTime.of(2026, 3, 13, 16, 0))
          .fechaCreacion(LocalDateTime.now())
          .listaAbierta(true)
          .estado(EstadoEleccion.LANZADA)
          .registrador(r2).build());

      Eleccion e3 = repositoryEleccion.save(Eleccion.builder()
          .nombre("Consulta Interpartidista 2026")
          .tipo("CONSULTA")
          .fechaInicio(LocalDateTime.of(2026, 2, 22, 8, 0))
          .fechaFinalizacion(LocalDateTime.of(2026, 2, 22, 16, 0))
          .fechaCreacion(LocalDateTime.now())
          .listaAbierta(false)
          .estado(EstadoEleccion.FINALIZADA)
          .registrador(r3).build());

      Eleccion e4 = repositoryEleccion.save(Eleccion.builder()
          .nombre("Elecciones Regionales Palmira 2026")
          .tipo("LEGISLATIVA")
          .fechaInicio(LocalDateTime.of(2026, 10, 27, 8, 0))
          .fechaFinalizacion(LocalDateTime.of(2026, 10, 27, 16, 0))
          .fechaCreacion(LocalDateTime.now())
          .listaAbierta(true)
          .estado(EstadoEleccion.CONFIGURACION)
          .registrador(r4).build());

      System.out.println("✅ Elecciones cargadas.");

      // ── 3. CIUDADANOS ────────────────────────────────────────────────
      Ciudadano c1 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Carlos Andrés Ríos Herrera").cedula("1005432100")
          .genero("M").votoObligatorio(true).build());

      Ciudadano c2 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Laura Milena Gómez Peña").cedula("1032456789")
          .genero("F").votoObligatorio(false).build());

      Ciudadano c3 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Juan David Martínez López").cedula("1018293847")
          .genero("M").votoObligatorio(true).build());

      Ciudadano c4 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Valentina Ruiz Castillo").cedula("1057384920")
          .genero("F").votoObligatorio(true).build());

      Ciudadano c5 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Andrés Felipe Vargas Torres").cedula("1094837261")
          .genero("M").votoObligatorio(false).build());

      Ciudadano c6 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("María José Moreno Salcedo").cedula("1012938475")
          .genero("F").votoObligatorio(true).build());

      System.out.println("✅ Ciudadanos cargados.");

      // ── 4. CANDIDATOS ────────────────────────────────────────────────
      repositoryCandidato.saveAll(List.of(
          Candidato.builder()
              .nombre("Gustavo Petro Urrego").numero("1")
              .fotoUrl("https://example.com/fotos/petro.jpg")
              .partidoLogoUrl("https://example.com/logos/colombia_humana.png")
              .registrador(r1).build(),

          Candidato.builder()
              .nombre("Francia Márquez Mina").numero("2")
              .fotoUrl("https://example.com/fotos/marquez.jpg")
              .partidoLogoUrl("https://example.com/logos/mais.png")
              .registrador(r1).build(),

          Candidato.builder()
              .nombre("Sergio Fajardo Valderrama").numero("3")
              .fotoUrl("https://example.com/fotos/fajardo.jpg")
              .partidoLogoUrl("https://example.com/logos/compromiso.png")
              .registrador(r2).build(),

          Candidato.builder()
              .nombre("Ingrid Betancourt Pulecio").numero("4")
              .fotoUrl("https://example.com/fotos/betancourt.jpg")
              .partidoLogoUrl("https://example.com/logos/verde_oxigeno.png")
              .registrador(r2).build()
      ));

      System.out.println("✅ Candidatos cargados.");

      // ── 5. ELECCION_JURADO ───────────────────────────────────────────
      // Solo se crean para e1 (la presidencial en CONFIGURACION)
      // Los demás sorteos pueden generarse por endpoint
      repositoryEleccionJurado.saveAll(List.of(
          EleccionJurado.builder()
              .ciudadano(c1).eleccion(e1)
              .tipoJurado(TipoJurado.URNA).numeroMesa(5)
              .fechaCapacitacion(LocalDateTime.of(2026, 5, 10, 9, 0))
              .asignado(true).build(),

          EleccionJurado.builder()
              .ciudadano(c2).eleccion(e1)
              .tipoJurado(TipoJurado.DOMICILIO).numeroMesa(12)
              .fechaCapacitacion(LocalDateTime.of(2026, 5, 10, 9, 0))
              .asignado(true).build(),

          EleccionJurado.builder()
              .ciudadano(c3).eleccion(e1)
              .tipoJurado(TipoJurado.URNA).numeroMesa(7)
              .fechaCapacitacion(LocalDateTime.of(2026, 5, 10, 10, 0))
              .asignado(false).build(),

          EleccionJurado.builder()
              .ciudadano(c4).eleccion(e1)
              .tipoJurado(TipoJurado.DOMICILIO).numeroMesa(20)
              .fechaCapacitacion(LocalDateTime.of(2026, 5, 10, 10, 0))
              .asignado(true).build()
      ));

      System.out.println("✅ EleccionJurado cargados.");
      System.out.println("🌱 Seed completado correctamente.");
    };
  }
}