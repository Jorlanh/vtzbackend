package com.votzz.backend.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.UUID;

@Data
@Entity
@Table(name = "poll_options") // CORREÇÃO: Mapeia para a tabela correta do SQL
@EqualsAndHashCode(callSuper = true)
public class VoteOption extends BaseEntity {
    
    // O ID e tenantId já vêm do BaseEntity
    
    @Column(name = "assembly_id")
    private UUID assemblyId;

    @Column(name = "descricao") // Mapeia o campo 'label' para a coluna 'descricao'
    private String label;
}