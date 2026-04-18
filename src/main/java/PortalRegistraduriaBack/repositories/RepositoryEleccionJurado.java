package PortalRegistraduriaBack.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import PortalRegistraduriaBack.entities.EleccionJurado;
import PortalRegistraduriaBack.enums.EstadoEleccionJurado;

@Repository
public interface RepositoryEleccionJurado extends JpaRepository<EleccionJurado, Long> {

  List<EleccionJurado> findByEleccion_IdEleccionOrderByNumeroMesaAscIdAsignacionJuradoAsc(Long idEleccion);

  boolean existsByEleccion_IdEleccion(Long idEleccion);

  boolean existsByEleccion_IdEleccionAndCiudadano_Cedula(Long idEleccion, String cedula);

  @Query("SELECT COUNT(ej) FROM EleccionJurado ej WHERE ej.eleccion.idEleccion = :idEleccion AND ej.estado = :estado")
  Long countByEleccionAndEstado(@Param("idEleccion") Long idEleccion, @Param("estado") EstadoEleccionJurado estado);
}
