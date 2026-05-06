package PortalRegistraduriaBack.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "PARTIDO")
public class Partido {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_partido")
  private Long idPartido;

  @Column(name = "nombre", nullable = false)
  private String nombre;

  @Column(name = "sigla", nullable = false)
  private String sigla;

  @Column(name = "fecha_creacion", nullable = false, updatable = false)
  private LocalDateTime fechaCreacion;

  @Column(name = "activo", nullable = false)
  private Boolean activo;

  // ARCHIVOS BINARIOS

  @Basic(fetch = FetchType.LAZY)
  @Column(name = "logo", columnDefinition = "bytea")
  private byte[] logo;

  @Basic(fetch = FetchType.LAZY)
  @Column(name = "estatutos", columnDefinition = "bytea")
  private byte[] estatutos;

  @Basic(fetch = FetchType.LAZY)
  @Column(name = "plataforma_ideologica", columnDefinition = "bytea")
  private byte[] plataformaIdeologica;

  @Basic(fetch = FetchType.LAZY)
  @Column(name = "registro_afiliados_directivos", columnDefinition = "bytea")
  private byte[] registroAfiliadosDirectivos;

  @Basic(fetch = FetchType.LAZY)
  @Column(name = "certificado_representatividad", columnDefinition = "bytea")
  private byte[] certificadoRepresentatividad;

  // RELACIONES ENTRE ENTIDADES

  @ManyToOne
  @JoinColumn(name = "id_registrador", referencedColumnName = "id_registrador", nullable = false)
  private Registrador registrador;

  @OneToMany(mappedBy = "partido")
  private List<Candidato> candidatos;

}
