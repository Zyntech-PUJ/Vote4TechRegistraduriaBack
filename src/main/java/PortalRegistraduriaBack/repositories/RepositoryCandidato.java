package PortalRegistraduriaBack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import PortalRegistraduriaBack.entities.Candidato;


@Repository
public interface RepositoryCandidato extends JpaRepository<Candidato, Long> {

  Candidato findByNumero(String numero);

  @Query(value = "SELECT foto FROM candidato WHERE id_candidato = :id", nativeQuery = true)
  byte[] findFotoById(@Param("id") Long id);

  @Query(value = "SELECT formulario_e6 FROM candidato WHERE id_candidato = :id", nativeQuery = true)
  byte[] findFormularioE6ById(@Param("id") Long id);

  @Query(value = "SELECT certificado_consejo_estado FROM candidato WHERE id_candidato = :id", nativeQuery = true)
  byte[] findCertificadoConsejoEstadoById(@Param("id") Long id);

  @Query(value = "SELECT cedula FROM candidato WHERE id_candidato = :id", nativeQuery = true)
  byte[] findCopiaCedulaById(@Param("id") Long id);

  @Query(value = "SELECT documento_aval FROM candidato WHERE id_candidato = :id", nativeQuery = true)
  byte[] findDocumentoAvalById(@Param("id") Long id);
}
