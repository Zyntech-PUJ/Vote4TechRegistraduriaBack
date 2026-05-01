package PortalRegistraduriaBack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PortalRegistraduriaBack.entities.ConsejoNacional;

@Repository
public interface RepositoryConsejoNacional extends JpaRepository<ConsejoNacional, Long> {

  ConsejoNacional findByUsername(String username);

}
