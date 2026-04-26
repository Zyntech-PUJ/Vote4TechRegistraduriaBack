package PortalRegistraduriaBack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PortalRegistraduriaBack.entities.Rol;
import java.util.Optional;


@Repository
public interface RepositoryRol extends JpaRepository<Rol, Long> {
  
  Optional<Rol> findByNombre(String nombre);
  
}
