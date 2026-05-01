package PortalRegistraduriaBack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PortalRegistraduriaBack.entities.Partido;

@Repository
public interface RepositoryPartido extends JpaRepository<Partido, Long> {
}
