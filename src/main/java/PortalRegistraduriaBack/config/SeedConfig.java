package PortalRegistraduriaBack.config;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import PortalRegistraduriaBack.entities.Candidato;
import PortalRegistraduriaBack.entities.CentroVotacion;
import PortalRegistraduriaBack.entities.Ciudadano;
import PortalRegistraduriaBack.entities.AdministradorElectoral;
import PortalRegistraduriaBack.entities.ConsejoNacional;
import PortalRegistraduriaBack.entities.Eleccion;
import PortalRegistraduriaBack.entities.EleccionJurado;
import PortalRegistraduriaBack.entities.Lista;
import PortalRegistraduriaBack.entities.Mesa;
import PortalRegistraduriaBack.entities.Partido;
import PortalRegistraduriaBack.entities.Registrador;
import PortalRegistraduriaBack.entities.Rol;
import PortalRegistraduriaBack.entities.UsuarioEntity;
import PortalRegistraduriaBack.enums.EstadoEleccion;
import PortalRegistraduriaBack.enums.EstadoEleccionJurado;
import PortalRegistraduriaBack.enums.TipoEleccion;
import PortalRegistraduriaBack.enums.TipoJurado;
import PortalRegistraduriaBack.enums.TipoLista;
import PortalRegistraduriaBack.enums.TipoMesa;
import PortalRegistraduriaBack.enums.TipoRol;
import PortalRegistraduriaBack.repositories.RepositoryCandidato;
import PortalRegistraduriaBack.repositories.RepositoryCentroVotacion;
import PortalRegistraduriaBack.repositories.RepositoryCiudadano;
import PortalRegistraduriaBack.repositories.RepositoryAdministradorElectoral;
import PortalRegistraduriaBack.repositories.RepositoryConsejoNacional;
import PortalRegistraduriaBack.repositories.RepositoryEleccion;
import PortalRegistraduriaBack.repositories.RepositoryEleccionJurado;
import PortalRegistraduriaBack.repositories.RepositoryLista;
import PortalRegistraduriaBack.repositories.RepositoryMesa;
import PortalRegistraduriaBack.repositories.RepositoryPartido;
import PortalRegistraduriaBack.repositories.RepositoryRegistrador;
import PortalRegistraduriaBack.repositories.RepositoryRol;
import PortalRegistraduriaBack.repositories.RepositoryUsuarioEntity;

@Configuration
public class SeedConfig {

  @Autowired
  private RepositoryPartido repositoryPartido;

  @Autowired
  private RepositoryRegistrador repositoryRegistrador;

  @Autowired
  private RepositoryAdministradorElectoral repositoryAdministradorElectoral;

  @Autowired
  private RepositoryConsejoNacional repositoryConsejoNacional;

  @Autowired
  private RepositoryEleccion repositoryEleccion;

  @Autowired
  private RepositoryCiudadano repositoryCiudadano;

  @Autowired
  private RepositoryCandidato repositoryCandidato;

  @Autowired
  private RepositoryLista repositoryLista;

  @Autowired
  private RepositoryEleccionJurado repositoryEleccionJurado;

  @Autowired
  private RepositoryRol repositoryRol;

  @Autowired
  private RepositoryUsuarioEntity repositoryUsuarioEntity;

  @Autowired
  private RepositoryCentroVotacion repositoryCentroVotacion;

  @Autowired
  private RepositoryMesa repositoryMesa;

  @Bean
  public CommandLineRunner seed() {

    return args -> {
      BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

      // 0. Se cargan roles
      if(repositoryRol.count() == 0) {
        repositoryRol.saveAll(
          List.of(
            new Rol(TipoRol.REGISTRADOR.name()),
            new Rol(TipoRol.ADMINISTRADOR_ELECTORAL.name()),
            new Rol(TipoRol.CONSEJO_NACIONAL.name())
          )
        );
      }

      AdministradorElectoral administradorElectoral;
      if (repositoryAdministradorElectoral.count() == 0) {
        administradorElectoral = AdministradorElectoral.builder()
            .usuario("adminElectoral")
            .password(passwordEncoder.encode("admin2026"))
            .build();

        UsuarioEntity usuarioEntityAdministrador = repositoryUsuarioEntity.save(
            UsuarioEntity.builder()
                .usuario(administradorElectoral.getUsuario())
                .password(administradorElectoral.getPassword())
                .roles(
                    List.of(
                        repositoryRol
                          .findByNombre(TipoRol.ADMINISTRADOR_ELECTORAL.name())
                          .orElse(new Rol(TipoRol.ADMINISTRADOR_ELECTORAL.name()))
                    )
                )
                .build()
        );

        administradorElectoral.setUsuarioEntity(usuarioEntityAdministrador);
        administradorElectoral = repositoryAdministradorElectoral.save(administradorElectoral);
      } else {
        administradorElectoral = repositoryAdministradorElectoral.findAll().get(0);
      }

      if (repositoryConsejoNacional.count() == 0) {
        ConsejoNacional consejoNacional = ConsejoNacional.builder()
            .username("consejoNacional")
            .password(passwordEncoder.encode("consejo2026"))
            .build();

        UsuarioEntity usuarioEntityConsejo = repositoryUsuarioEntity.save(
            UsuarioEntity.builder()
                .usuario(consejoNacional.getUsername())
                .password(consejoNacional.getPassword())
                .roles(
                    List.of(
                        repositoryRol
                            .findByNombre(TipoRol.CONSEJO_NACIONAL.name())
                            .orElse(new Rol(TipoRol.CONSEJO_NACIONAL.name()))
                    )
                )
                .build()
        );

        consejoNacional.setUsuarioEntity(usuarioEntityConsejo);
        repositoryConsejoNacional.save(consejoNacional);
      }

      if (repositoryRegistrador.count() > 0)
        return;

      // ── 1. REGISTRADORES ─────────────────────────────────────────────
      Registrador r0 = Registrador.builder()
        .nombre("Usuario Test")
        .usuario("test123")
        .password(passwordEncoder.encode("12345"))
        .build();

      // Los demás registradores con passwords hasheadas también
      Registrador r1 = Registrador.builder()
          .nombre("Ana Patricia Suárez").usuario("asuarez")
          .password(passwordEncoder.encode("asuarez2026")).build();

      Registrador r2 = Registrador.builder()
          .nombre("Jorge Iván Medina").usuario("jmedina")
          .password(passwordEncoder.encode("jmedina2026")).build();

      Registrador r3 = Registrador.builder()
          .nombre("Lucía Fernanda Ríos").usuario("lrios")
          .password(passwordEncoder.encode("lrios2026")).build();

      Registrador r4 = Registrador.builder()
          .nombre("Camilo Ernesto Vega").usuario("cvega")
          .password(passwordEncoder.encode("cvega2026")).build();

      repositoryRegistrador.saveAll(List.of(r1,r2,r3,r4));

      // -- 2. AdministradorElectoral -------

      UsuarioEntity ue0 = repositoryUsuarioEntity.save(
        UsuarioEntity.builder()
        .usuario(r0.getUsuario())
        .password(r0.getPassword())
        .roles(
          List.of(
            repositoryRol
            .findByNombre(TipoRol.REGISTRADOR.name())
            .orElse(new Rol(TipoRol.REGISTRADOR.name()))
          )
        )
        .build()
      );
      repositoryUsuarioEntity.save(ue0);

      r0.setUsuarioEntity(ue0);
      repositoryRegistrador.save(r0);

      UsuarioEntity ue1 = repositoryUsuarioEntity.save(
        UsuarioEntity.builder()
        .usuario(r1.getUsuario())
        .password(r1.getPassword())
        .roles(
          List.of(
            repositoryRol
            .findByNombre(TipoRol.REGISTRADOR.name())
            .orElse(new Rol(TipoRol.REGISTRADOR.name()))
          )
        )
        .build()
      );
      repositoryUsuarioEntity.save(ue1);

      r1.setUsuarioEntity(ue1);
      repositoryRegistrador.save(r1);

      UsuarioEntity ue2 = repositoryUsuarioEntity.save(
        UsuarioEntity.builder()
        .usuario(r2.getUsuario())
        .password(r2.getPassword())
        .roles(
          List.of(
            repositoryRol
            .findByNombre(TipoRol.REGISTRADOR.name())
            .orElse(new Rol(TipoRol.REGISTRADOR.name()))
          )
        )
        .build()
      );
      repositoryUsuarioEntity.save(ue2);

      r2.setUsuarioEntity(ue2);
      repositoryRegistrador.save(r2);

      UsuarioEntity ue3 = repositoryUsuarioEntity.save(
        UsuarioEntity.builder()
        .usuario(r3.getUsuario())
        .password(r3.getPassword())
        .roles(
          List.of(
            repositoryRol
            .findByNombre(TipoRol.REGISTRADOR.name())
            .orElse(new Rol(TipoRol.REGISTRADOR.name()))
          )
        )
        .build()
      );
      repositoryUsuarioEntity.save(ue3);

      r3.setUsuarioEntity(ue3);
      repositoryRegistrador.save(r3);

      UsuarioEntity ue4 = repositoryUsuarioEntity.save(
        UsuarioEntity.builder()
        .usuario(r4.getUsuario())
        .password(r4.getPassword())
        .roles(
          List.of(
            repositoryRol
            .findByNombre(TipoRol.REGISTRADOR.name())
            .orElse(new Rol(TipoRol.REGISTRADOR.name()))
          )
        )
        .build()
      );
      repositoryUsuarioEntity.save(ue4);

      r4.setUsuarioEntity(ue4);
      repositoryRegistrador.save(r4);

      System.out.println("✅ Registradores cargados.");

      // ── 2. ELECCIONES ────────────────────────────────────────────────
      Eleccion e1 = repositoryEleccion.save(Eleccion.builder()
          .nombre("Elecciones Presidenciales 2026")
          .tipo(TipoEleccion.PRESIDENCIAL)
          .fechaInicio(LocalDateTime.of(2026, 5, 25, 8, 0))
          .fechaFinalizacion(LocalDateTime.of(2026, 5, 25, 16, 0))
          .fechaInicioUrna(LocalDateTime.of(2026, 5, 25, 8, 0))
          .fechaFinalizacionUrna(LocalDateTime.of(2026, 5, 25, 16, 0))
          .fechaInicioDomicilio(LocalDateTime.of(2026, 5, 20, 8, 0))
          .fechaFinalizacionDomicilio(LocalDateTime.of(2026, 5, 24, 16, 0))
          .fechaCreacion(LocalDateTime.now())
          .listaAbierta(false)
          .estado(EstadoEleccion.CONFIGURACION)
          .administradorElectoral(administradorElectoral)
          .build());

      Eleccion e2 = repositoryEleccion.save(Eleccion.builder()
          .nombre("Elecciones Legislativas 2026")
          .tipo(TipoEleccion.LEGISLATIVA)
          .fechaInicio(LocalDateTime.of(2026, 3, 13, 8, 0))
          .fechaFinalizacion(LocalDateTime.of(2026, 3, 13, 16, 0))
          .fechaInicioUrna(LocalDateTime.of(2026, 3, 13, 8, 0))
          .fechaFinalizacionUrna(LocalDateTime.of(2026, 3, 13, 16, 0))
          .fechaInicioDomicilio(LocalDateTime.of(2026, 3, 8, 8, 0))
          .fechaFinalizacionDomicilio(LocalDateTime.of(2026, 3, 12, 16, 0))
          .fechaCreacion(LocalDateTime.now())
          .listaAbierta(true)
          .estado(EstadoEleccion.LANZADA)
          .administradorElectoral(administradorElectoral)
          .build());

      Eleccion e3 = repositoryEleccion.save(Eleccion.builder()
          .nombre("Consulta Interpartidista 2026")
          .tipo(TipoEleccion.CONSULTA)
          .fechaInicio(LocalDateTime.of(2026, 2, 22, 8, 0))
          .fechaFinalizacion(LocalDateTime.of(2026, 2, 22, 16, 0))
          .fechaInicioUrna(LocalDateTime.of(2026, 2, 22, 8, 0))
          .fechaFinalizacionUrna(LocalDateTime.of(2026, 2, 22, 16, 0))
          .fechaInicioDomicilio(LocalDateTime.of(2026, 2, 17, 8, 0))
          .fechaFinalizacionDomicilio(LocalDateTime.of(2026, 2, 21, 16, 0))
          .fechaCreacion(LocalDateTime.now())
          .listaAbierta(false)
          .estado(EstadoEleccion.FINALIZADA)
          .administradorElectoral(administradorElectoral)
          .build());

      Eleccion e4 = repositoryEleccion.save(Eleccion.builder()
          .nombre("Elecciones Regionales Palmira 2026")
          .tipo(TipoEleccion.LEGISLATIVA)
          .fechaInicio(LocalDateTime.of(2026, 10, 27, 8, 0))
          .fechaFinalizacion(LocalDateTime.of(2026, 10, 27, 16, 0))
          .fechaInicioUrna(LocalDateTime.of(2026, 10, 27, 8, 0))
          .fechaFinalizacionUrna(LocalDateTime.of(2026, 10, 27, 16, 0))
          .fechaInicioDomicilio(LocalDateTime.of(2026, 10, 22, 8, 0))
          .fechaFinalizacionDomicilio(LocalDateTime.of(2026, 10, 26, 16, 0))
          .fechaCreacion(LocalDateTime.now())
          .listaAbierta(true)
          .estado(EstadoEleccion.CONFIGURACION)
          .administradorElectoral(administradorElectoral)
          .build());

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

      // -- 4.5 PARTIDOS -------
      Partido p1 = Partido.builder()
        .nombre("Centro Democratico")
        .sigla("CD")
        .fechaCreacion(LocalDateTime.now())
        .activo(false)
        .registrador(r1)
        .build();

      repositoryPartido.save(p1);

      // -- 4.6 LISTAS -------
      Lista l1 = repositoryLista.save(
        Lista.builder()
          .tipo(TipoLista.CERRADA)
          .fechaCreacion(LocalDateTime.now())
          .fechaModificacion(LocalDateTime.now())
          .eleccion(e1)
          .build()
      );

      Lista l2 = repositoryLista.save(
        Lista.builder()
          .tipo(TipoLista.CERRADA)
          .fechaCreacion(LocalDateTime.now())
          .fechaModificacion(LocalDateTime.now())
          .eleccion(e2)
          .build()
      );

      // ── 4. CANDIDATOS ────────────────────────────────────────────────
      Candidato cn1 = Candidato.builder()
        .nombre("Gustavo Petro Urrego").numero("1")
        .activo(false)
        .registrador(r1)
        .lista(l1)
        .partido(p1)
        .build();

      Candidato cn2 = Candidato.builder()
        .nombre("Francia Márquez Mina").numero("2")
        .activo(false)
        .registrador(r1)
        .lista(l1)
        .partido(p1)
        .build();

      Candidato cn3 = Candidato.builder()
        .nombre("Sergio Fajardo Valderrama").numero("3")
        .activo(false)
        .registrador(r1)
        .lista(l2)
        .partido(p1)
        .build();

      Candidato cn4 = Candidato.builder()
        .nombre("Ingrid Betancourt Pulecio").numero("4")
        .activo(false)
        .registrador(r1)
        .lista(l2)
        .partido(p1)
        .build();

      repositoryCandidato.saveAll(List.of(cn1, cn2, cn3, cn4));

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

      // ── 6. CENTRO DE VOTACION Y MESAS ────────────────────────────────
      CentroVotacion cv1 = CentroVotacion.builder()
          .nombre("Colegio San José")
          .direccion("Calle 10 # 5-23")
          .ciudad("Bogotá")
          .departamento("Cundinamarca")
          .activo(true)
          .build();
      repositoryCentroVotacion.save(cv1);

      repositoryMesa.saveAll(List.of(
          Mesa.builder().numero(1).tipo(TipoMesa.URNA).activo(true).centroVotacion(cv1).build(),
          Mesa.builder().numero(2).tipo(TipoMesa.URNA).activo(true).centroVotacion(cv1).build(),
          Mesa.builder().numero(3).tipo(TipoMesa.URNA).activo(true).centroVotacion(cv1).build(),
          Mesa.builder().numero(4).tipo(TipoMesa.URNA).activo(true).centroVotacion(cv1).build(),
          Mesa.builder().numero(5).tipo(TipoMesa.URNA).activo(true).centroVotacion(cv1).build(),
          Mesa.builder().numero(1).tipo(TipoMesa.DOMICILIO).activo(true).centroVotacion(cv1).build()
      ));

      System.out.println("✅ CentroVotacion y Mesas cargados.");
      System.out.println("🌱 Seed completado correctamente.");
    };
  }
}
