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
import PortalRegistraduriaBack.enums.EstadoEleccionJurado;
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
      if (repositoryRegistrador.count() > 0)
        return;

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

      Ciudadano c7 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Santiago Gómez Ramírez").cedula("1045678923")
          .genero("M").votoObligatorio(true).build());

      Ciudadano c8 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Daniela Fernández Castro").cedula("1067891234")
          .genero("F").votoObligatorio(false).build());

      Ciudadano c9 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Sebastián Ortega Muñoz").cedula("1023456780")
          .genero("M").votoObligatorio(true).build());

      Ciudadano c10 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Camila Díaz Quintero").cedula("1089012345")
          .genero("F").votoObligatorio(true).build());

      Ciudadano c11 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Julián Pérez Aguilar").cedula("1011234567")
          .genero("M").votoObligatorio(false).build());

      Ciudadano c12 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Isabella Soto Medina").cedula("1078901234")
          .genero("F").votoObligatorio(true).build());

      Ciudadano c13 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Nicolás Herrera Jiménez").cedula("1034567890")
          .genero("M").votoObligatorio(true).build());

      Ciudadano c14 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Sofía Castellanos Ávila").cedula("1056789012")
          .genero("F").votoObligatorio(false).build());

      Ciudadano c15 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Felipe Mendoza Restrepo").cedula("1000123456")
          .genero("M").votoObligatorio(true).build());

      Ciudadano c16 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Manuela Rojas Ospina").cedula("1023459876")
          .genero("F").votoObligatorio(true).build());

      Ciudadano c17 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("David Alejandro Cruz Patiño").cedula("1098765432")
          .genero("M").votoObligatorio(false).build());

      Ciudadano c18 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Natalia Vergara Londoño").cedula("1043217654")
          .genero("F").votoObligatorio(true).build());

      Ciudadano c19 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Luis Miguel Cárdenas Suárez").cedula("1065432198")
          .genero("M").votoObligatorio(true).build());

      Ciudadano c20 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Paola Andrea Beltrán Niño").cedula("1087654321")
          .genero("F").votoObligatorio(false).build());

      Ciudadano c21 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Esteban Mora Gutiérrez").cedula("1009876543")
          .genero("M").votoObligatorio(true).build());

      Ciudadano c22 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Alejandra Pineda Acosta").cedula("1031122334")
          .genero("F").votoObligatorio(true).build());

      Ciudadano c23 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Mateo Salazar Bermúdez").cedula("1052233445")
          .genero("M").votoObligatorio(false).build());

      Ciudadano c24 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Gabriela Lozano Cifuentes").cedula("1073344556")
          .genero("F").votoObligatorio(true).build());

      Ciudadano c25 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Tomás Arbeláez Montes").cedula("1014455667")
          .genero("M").votoObligatorio(true).build());

      Ciudadano c26 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Luciana Quintero Bejarano").cedula("1035566778")
          .genero("F").votoObligatorio(false).build());

      Ciudadano c27 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Samuel Ibáñez Parra").cedula("1056677889")
          .genero("M").votoObligatorio(true).build());

      Ciudadano c28 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Valeria Escobar Zapata").cedula("1077788990")
          .genero("F").votoObligatorio(true).build());

      Ciudadano c29 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Emilio Contreras Naranjo").cedula("1018899001")
          .genero("M").votoObligatorio(false).build());

      Ciudadano c30 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Mariana Fuentes Calderón").cedula("1039900112")
          .genero("F").votoObligatorio(true).build());

      Ciudadano c31 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Jerónimo Salcedo Prado").cedula("1060011223")
          .genero("M").votoObligatorio(true).build());

      Ciudadano c32 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Simona Arias Velásquez").cedula("1081122334")
          .genero("F").votoObligatorio(false).build());

      Ciudadano c33 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Rodrigo Castaño Mejía").cedula("1022233445")
          .genero("M").votoObligatorio(true).build());

      Ciudadano c34 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Catalina Mora Henao").cedula("1043344556")
          .genero("F").votoObligatorio(true).build());

      Ciudadano c35 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Iván Darío Ospina Cano").cedula("1064455667")
          .genero("M").votoObligatorio(false).build());

      Ciudadano c36 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Renata Guerrero Tobón").cedula("1085566778")
          .genero("F").votoObligatorio(true).build());

      Ciudadano c37 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Mauricio Ríos Londoño").cedula("1026677889")
          .genero("M").votoObligatorio(true).build());

      Ciudadano c38 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Alejandro Peñuela Duque").cedula("1047788990")
          .genero("M").votoObligatorio(false).build());

      Ciudadano c39 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Margarita Rosa Pinto Uribe").cedula("1068899001")
          .genero("F").votoObligatorio(true).build());

      Ciudadano c40 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Cristian Camilo Zapata Gil").cedula("1089900112")
          .genero("M").votoObligatorio(true).build());

      Ciudadano c41 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Yesenia Paola Cano Ríos").cedula("1030011223")
          .genero("F").votoObligatorio(false).build());

      Ciudadano c42 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Harold Stiven Muñoz Bernal").cedula("1051122334")
          .genero("M").votoObligatorio(true).build());

      Ciudadano c43 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Diana Marcela Varón Trujillo").cedula("1072233445")
          .genero("F").votoObligatorio(true).build());

      Ciudadano c44 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Nelson Eduardo Jaramillo Soto").cedula("1013344556")
          .genero("M").votoObligatorio(false).build());

      Ciudadano c45 = repositoryCiudadano.save(Ciudadano.builder()
          .nombre("Viviana Liseth Agudelo Mora").cedula("1034455667")
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
              .registrador(r2).build()));

      System.out.println("✅ Candidatos cargados.");

      // ── 5. ELECCION_JURADO ───────────────────────────────────────────
      // Fecha de referencia: hoy es 17/04/2026
      //
      // CAPACITADO → fechaCapacitacion hace más de 1 día (pasada) + estado=CAPACITADO
      // NO_PRESENTADO → fechaCapacitacion hace más de 1 día (pasada) +
      // estado=NO_PRESENTADO
      // PENDIENTE → fechaCapacitacion futura (aún no llega el día) + estado=PENDIENTE

      LocalDateTime pasadaCapacitado = LocalDateTime.of(2026, 4, 10, 9, 0); // hace 7 días
      LocalDateTime pasadaNoPresentado = LocalDateTime.of(2026, 4, 14, 9, 0); // hace 3 días
      LocalDateTime futura = LocalDateTime.of(2026, 5, 10, 9, 0); // en el futuro

      repositoryEleccionJurado.saveAll(List.of(

          // e1 - Presidencial: 2 capacitados, 2 no presentados, 2 pendientes
          EleccionJurado.builder()
              .ciudadano(c1).eleccion(e1).tipoJurado(TipoJurado.URNA).numeroMesa(5)
              .fechaCapacitacion(pasadaCapacitado)
              .estado(EstadoEleccionJurado.CAPACITADO).build(),

          EleccionJurado.builder()
              .ciudadano(c2).eleccion(e1).tipoJurado(TipoJurado.DOMICILIO).numeroMesa(12)
              .fechaCapacitacion(pasadaCapacitado)
              .estado(EstadoEleccionJurado.CAPACITADO).build(),

          EleccionJurado.builder()
              .ciudadano(c3).eleccion(e1).tipoJurado(TipoJurado.URNA).numeroMesa(7)
              .fechaCapacitacion(pasadaNoPresentado)
              .estado(EstadoEleccionJurado.NO_PRESENTADO).build(),

          EleccionJurado.builder()
              .ciudadano(c4).eleccion(e1).tipoJurado(TipoJurado.DOMICILIO).numeroMesa(20)
              .fechaCapacitacion(pasadaNoPresentado)
              .estado(EstadoEleccionJurado.NO_PRESENTADO).build(),

          EleccionJurado.builder()
              .ciudadano(c5).eleccion(e1).tipoJurado(TipoJurado.URNA).numeroMesa(3)
              .fechaCapacitacion(futura)
              .estado(EstadoEleccionJurado.PENDIENTE).build(),

          EleccionJurado.builder()
              .ciudadano(c6).eleccion(e1).tipoJurado(TipoJurado.DOMICILIO).numeroMesa(18)
              .fechaCapacitacion(futura)
              .estado(EstadoEleccionJurado.PENDIENTE).build(),

          // e2 - Legislativa: datos variados para el dashboard
          EleccionJurado.builder()
              .ciudadano(c1).eleccion(e2).tipoJurado(TipoJurado.URNA).numeroMesa(2)
              .fechaCapacitacion(pasadaCapacitado)
              .estado(EstadoEleccionJurado.CAPACITADO).build(),

          EleccionJurado.builder()
              .ciudadano(c3).eleccion(e2).tipoJurado(TipoJurado.DOMICILIO).numeroMesa(9)
              .fechaCapacitacion(pasadaNoPresentado)
              .estado(EstadoEleccionJurado.NO_PRESENTADO).build(),

          EleccionJurado.builder()
              .ciudadano(c5).eleccion(e2).tipoJurado(TipoJurado.URNA).numeroMesa(15)
              .fechaCapacitacion(futura)
              .estado(EstadoEleccionJurado.PENDIENTE).build()));

      System.out.println("✅ EleccionJurado cargados.");
      System.out.println("🌱 Seed completado correctamente.");
    };
  }
}