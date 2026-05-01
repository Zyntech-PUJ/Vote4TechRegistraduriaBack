package PortalRegistraduriaBack.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "ADMINISTRADOR_ELECTORAL")
public class AdministradorElectoral {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_administrador_electoral")
  private Long idAdministradorElectoral;

  @Column(name = "usuario", nullable = false, unique = true, length = 32)
  private String usuario;

  @Column(name = "password", nullable = false)
  private String password;

  @OneToMany(mappedBy = "administradorElectoral", cascade = CascadeType.ALL)
  private List<Eleccion> elecciones;

  @OneToOne
  @JoinColumn(name = "id_usuario_entity")
  private UsuarioEntity usuarioEntity;
}
