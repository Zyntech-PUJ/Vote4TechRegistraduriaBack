package PortalRegistraduriaBack.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PortalRegistraduriaBack.entities.UsuarioEntity;

@Repository
public interface RepositoryUsuarioEntity extends JpaRepository<UsuarioEntity, Long> {
  Optional<UsuarioEntity> findByUsuario(String usuario);

  Boolean existsByUsuario(String usuario);
}
