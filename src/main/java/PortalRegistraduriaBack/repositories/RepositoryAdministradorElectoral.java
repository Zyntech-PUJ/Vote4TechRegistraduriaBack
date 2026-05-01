package PortalRegistraduriaBack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PortalRegistraduriaBack.entities.AdministradorElectoral;

@Repository
public interface RepositoryAdministradorElectoral extends JpaRepository<AdministradorElectoral, Long> {

  AdministradorElectoral findByUsuario(String usuario);

}
