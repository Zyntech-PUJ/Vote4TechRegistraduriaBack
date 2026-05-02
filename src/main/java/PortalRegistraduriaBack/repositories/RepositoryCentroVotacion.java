package PortalRegistraduriaBack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PortalRegistraduriaBack.entities.CentroVotacion;

@Repository
public interface RepositoryCentroVotacion extends JpaRepository<CentroVotacion, Long> {

}
