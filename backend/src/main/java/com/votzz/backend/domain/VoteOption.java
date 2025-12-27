package com.votzz.backend.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
<<<<<<< HEAD

@Data
@Entity
@Table(name = "poll_options") // <--- CORREÇÃO: O nome no banco é 'poll_options'
@EqualsAndHashCode(callSuper = true)
public class VoteOption extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "tenant_id", insertable = false, updatable = false)
    private Tenant tenant;

    @ManyToOne
    @JoinColumn(name = "assembly_id")
    private Assembly assembly;

    @Column(nullable = false)
    private String descricao;
=======
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
>>>>>>> 1d628f725aadaeeb6666b2f0266d411aed625f25
}