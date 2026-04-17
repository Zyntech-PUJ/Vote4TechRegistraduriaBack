package PortalRegistraduriaBack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import PortalRegistraduriaBack.entities.Candidato;


@Repository
public interface RepositoryCandidato extends JpaRepository<Candidato, Long> {
  
  public Candidato findByNumero(String numero);

}
