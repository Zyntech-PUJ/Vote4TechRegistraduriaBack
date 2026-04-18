package PortalRegistraduriaBack.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PortalRegistraduriaBack.entities.EleccionJurado;

@Repository
public interface RepositoryEleccionJurado extends JpaRepository<EleccionJurado, Long> {

  List<EleccionJurado> findByEleccion_IdEleccionOrderByNumeroMesaAscIdAsignacionJuradoAsc(Long idEleccion);

  boolean existsByEleccion_IdEleccion(Long idEleccion);

  boolean existsByEleccion_IdEleccionAndCiudadano_Cedula(Long idEleccion, String cedula);
}
