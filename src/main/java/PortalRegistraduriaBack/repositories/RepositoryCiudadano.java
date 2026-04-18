package PortalRegistraduriaBack.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PortalRegistraduriaBack.entities.Ciudadano;

@Repository
public interface RepositoryCiudadano extends JpaRepository<Ciudadano, Long> {

  List<Ciudadano> findAllByOrderByIdCiudadanoAsc();

  java.util.Optional<Ciudadano> findByCedula(String cedula);
}
