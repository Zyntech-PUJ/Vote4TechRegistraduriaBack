package PortalRegistraduriaBack.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PortalRegistraduriaBack.entities.Mesa;
import PortalRegistraduriaBack.enums.TipoMesa;

@Repository
public interface RepositoryMesa extends JpaRepository<Mesa, Long> {

  List<Mesa> findByCentroVotacion_IdCentroVotacion(Long idCentroVotacion);

  List<Mesa> findByCentroVotacion_IdCentroVotacionAndTipo(Long idCentroVotacion, TipoMesa tipo);

}
