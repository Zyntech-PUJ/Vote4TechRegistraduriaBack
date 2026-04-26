package PortalRegistraduriaBack.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USUARIOS_ENTITY")
public class UsuarioEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idUsuarioEntity;
  
  private String usuario;
  private String password;
  
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "USUARIOS_ROLES",
    joinColumns = @JoinColumn(name = "id_usuario_entity", referencedColumnName = "idUsuarioEntity"),
    inverseJoinColumns = @JoinColumn(name = "id_rol", referencedColumnName = "idRol")
  )
  private List<Rol> roles;
  
}

