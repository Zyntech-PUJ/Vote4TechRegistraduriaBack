package PortalRegistraduriaBack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import PortalRegistraduriaBack.entities.Partido;

@Repository
public interface RepositoryPartido extends JpaRepository<Partido, Long> {

  @Query(value = "SELECT logo FROM partido WHERE id_partido = :id", nativeQuery = true)
  byte[] findLogoById(@Param("id") Long id);

  @Query(value = "SELECT estatutos FROM partido WHERE id_partido = :id", nativeQuery = true)
  byte[] findEstatutosById(@Param("id") Long id);

  @Query(value = "SELECT plataforma_ideologica FROM partido WHERE id_partido = :id", nativeQuery = true)
  byte[] findPlataformaIdeologicaById(@Param("id") Long id);

  @Query(value = "SELECT registro_afiliados_directivos FROM partido WHERE id_partido = :id", nativeQuery = true)
  byte[] findRegistroAfiliadosDirectivosById(@Param("id") Long id);

  @Query(value = "SELECT certificado_representatividad FROM partido WHERE id_partido = :id", nativeQuery = true)
  byte[] findCertificadoRepresentatividadById(@Param("id") Long id);
}
