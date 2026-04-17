package PortalRegistraduriaBack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PortalRegistraduriaBack.entities.Registrador;


@Repository
public interface RepositoryRegistrador extends JpaRepository<Registrador, Long> {

  public Registrador findByUsuario(String usuario);

}