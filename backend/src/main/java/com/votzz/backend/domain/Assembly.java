package com.votzz.backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

@Entity
@Table(name = "assemblies")
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
    }
}