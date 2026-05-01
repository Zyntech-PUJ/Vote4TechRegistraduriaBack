package PortalRegistraduriaBack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PortalRegistraduriaBack.entities.Lista;

@Repository
public interface RepositoryLista extends JpaRepository<Lista, Long> {
}
