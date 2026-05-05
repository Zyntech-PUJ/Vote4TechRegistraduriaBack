package PortalRegistraduriaBack.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "CANDIDATO")
public class Candidato {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_candidato")
  private Long idCandidato;

  @Column(name = "nombre", nullable = false, length = 32)
  private String nombre;

  @Column(name = "numero", nullable = false, length = 16)
  private String numero;

  @Column(name = "activo", nullable = false)
  private Boolean activo;

  // ARCHIVOS BINARIOS

  @Basic(fetch = FetchType.LAZY)
  @Column(name = "foto", columnDefinition = "bytea")
  private byte[] foto;

  @Basic(fetch = FetchType.LAZY)
  @Column(name = "formulario_e6", columnDefinition = "bytea")
  private byte[] formularioE6;

  @Basic(fetch = FetchType.LAZY)
  @Column(name = "certificado_consejo_estado", columnDefinition = "bytea")
  private byte[] certificadoConsejoEstado;

  @Basic(fetch = FetchType.LAZY)
  @Column(name = "cedula", columnDefinition = "bytea")
  private byte[] copiaCedula;

  @Basic(fetch = FetchType.LAZY)
  @Column(name = "documento_aval", columnDefinition = "bytea")
  private byte[] documentoAval;

  // RELACIONES ENTRE ENTIDADES

  @ManyToOne
  @JoinColumn(name = "id_registrador", referencedColumnName = "id_registrador", nullable = false)
  private Registrador registrador;

  @ManyToOne
  @JoinColumn(name = "id_lista", referencedColumnName = "id_lista", nullable = false)
  private Lista lista;

  @ManyToOne
  @JoinColumn(name = "id_partido", referencedColumnName = "id_partido", nullable = false)
  private Partido partido;

}
