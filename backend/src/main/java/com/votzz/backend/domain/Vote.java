package com.votzz.backend.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "votes", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"assembly_id", "user_id"})
})
@Data 
@EqualsAndHashCode(callSuper = true)
public class Vote extends BaseEntity {
    // O campo 'id' já existe na BaseEntity como UUID

    @ManyToOne
    @JoinColumn(name = "assembly_id")
    private Assembly assembly;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "opcao_escolhida")
    private String opcaoEscolhida; 

    @Column(name = "data_voto")
    private LocalDateTime timestamp;
    
    @Column(name = "audit_hash")
    private String auditHash; 
}