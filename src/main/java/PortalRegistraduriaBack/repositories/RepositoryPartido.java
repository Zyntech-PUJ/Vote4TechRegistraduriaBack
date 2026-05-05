package PortalRegistraduriaBack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import PortalRegistraduriaBack.entities.Partido;

@Repository
public interface RepositoryPartido extends JpaRepository<Partido, Long> {
  @Query("SELECT p.logo FROM Partido p WHERE p.idPartido = :id")
  byte[] findLogoById(@Param("id") Long id);

  @Query("SELECT p.estatutos FROM Partido p WHERE p.idPartido = :id")
  byte[] findEstatutosById(@Param("id") Long id);

  @Query("SELECT p.plataformaIdeologica FROM Partido p WHERE p.idPartido = :id")
  byte[] findPlataformaIdeologicaById(@Param("id") Long id);

  @Query("SELECT p.registroAfiliadosDirectivos FROM Partido p WHERE p.idPartido = :id")
  byte[] findRegistroAfiliadosDirectivosById(@Param("id") Long id);

  @Query("SELECT p.certificadoRepresentatividad FROM Partido p WHERE p.idPartido = :id")
  byte[] findCertificadoRepresentatividadById(@Param("id") Long id);
}
