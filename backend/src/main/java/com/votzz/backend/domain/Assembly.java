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

    private String titulo;
    private String description;
    
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    
    private String linkVideoConferencia;

    @Enumerated(EnumType.STRING)
    private StatusAssembly status;

    // CORREÇÃO: Campo tenant adicionado
    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    public enum StatusAssembly {
        AGENDADA, ABERTA, ENCERRADA
    }
}