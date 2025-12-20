package com.votzz.backend.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "assemblies")
@Data
@EqualsAndHashCode(callSuper = true)
public class Assembly extends BaseEntity {
    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String titulo;
    
    @Column(length = 2000)
    private String descricao;
    
    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;

    @Column(name = "data_fim")
    private LocalDateTime dataFim;
    
    // CORREÇÃO: Mapeando para o nome exato no seu SQL (link_meet)
    @Column(name = "link_meet") 
    private String linkVideoConferencia; 
    
    @Enumerated(EnumType.STRING)
    private StatusAssembly status; 

    public enum StatusAssembly { ABERTA, ENCERRADA, AGENDADA }
}