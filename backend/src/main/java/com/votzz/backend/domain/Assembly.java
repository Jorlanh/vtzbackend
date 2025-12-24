package com.votzz.backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "assemblies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Assembly {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "titulo") // Sincronizado com SQL
    private String titulo;

    private String description;
    
    @Column(name = "data_inicio") // Sincronizado com SQL
    private LocalDateTime dataInicio;

    @Column(name = "data_fim") // Sincronizado com SQL
    private LocalDateTime dataFim;
    
    @Column(name = "link_video_conferencia")
    private String linkVideoConferencia;

    @Enumerated(EnumType.STRING)
    private StatusAssembly status;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @Column(name = "anexo_url") // Sincronizado com SQL (nome atualizado de attachment_url)
    private String attachmentUrl;

    public enum StatusAssembly {
        AGENDADA, ABERTA, ENCERRADA
    }
}