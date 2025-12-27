package com.votzz.backend.domain;

import jakarta.persistence.*;
<<<<<<< HEAD
import lombok.Data;
import lombok.EqualsAndHashCode;
=======
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
>>>>>>> 1d628f725aadaeeb6666b2f0266d411aed625f25
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

@Data
@Entity
@Table(name = "assemblies")
<<<<<<< HEAD
@EqualsAndHashCode(callSuper = true)
public class Assembly extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "tenant_id", insertable = false, updatable = false)
    private Tenant tenant;

    @Column(nullable = false)
    private String titulo;

    @Column(name = "description") 
    private String descricao;

    @Column(name = "data_inicio", nullable = false)
    private LocalDateTime dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDateTime dataFim;

    @Column(name = "link_video_conferencia")
    private String linkVideoConferencia;

    // --- CORREÇÃO: Uso de Enum em vez de String ---
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusAssembly status; 

    @Column(name = "anexo_url")
    private String anexoUrl;

    @Column(name = "tipo_assembleia")
    private String tipoAssembleia; 

    @Column(name = "quorum_type")
    private String quorumType;

    @Column(name = "vote_type")
    private String voteType;

    @Column(name = "vote_privacy")
    private String votePrivacy;

    // --- DEFINIÇÃO DO ENUM ---
    public enum StatusAssembly {
        AGENDADA, ABERTA, ENCERRADA, CANCELADA
=======
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Assembly {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "titulo", nullable = false) // Mapeia para a coluna 'titulo' do seu DDL
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "data_inicio", nullable = false) // Mapeia para 'data_inicio'
    private LocalDateTime dataInicio;

    @Column(name = "data_fim", nullable = false) // Mapeia para 'data_fim'
    private LocalDateTime dataFim;
    
    @Column(name = "link_video_conferencia")
    private String linkVideoConferencia;

    // Alterado para String para evitar erros de cast caso o banco use VARCHAR simples
    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @Column(name = "anexo_url") // Mapeia para a coluna 'anexo_url' do seu DDL
    private String attachmentUrl;

    /**
     * CORREÇÃO CRÍTICA: O mappedBy deve ser exatamente o nome do atributo 
     * dentro da classe Vote.java que referencia esta classe.
     * Na classe Vote, o campo deve ser: private Assembly assembly;
     */
    @OneToMany(mappedBy = "assembly", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vote> votes;

    // Mantido conforme sua lógica de negócio
    public enum StatusAssembly {
        AGENDADA, ABERTA, ENCERRADA
>>>>>>> 1d628f725aadaeeb6666b2f0266d411aed625f25
    }
}