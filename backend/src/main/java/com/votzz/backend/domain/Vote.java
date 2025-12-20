package com.votzz.backend.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "votes", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"assembly_id", "user_id"})
})
@Data 
@EqualsAndHashCode(callSuper = true)
public class Vote extends BaseEntity {
    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "assembly_id")
    private Assembly assembly;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "opcao_escolhida")
    private String opcaoEscolhida; 

    // CORREÇÃO: Mapeando 'timestamp' do Java para 'data_voto' do SQL
    @Column(name = "data_voto")
    private LocalDateTime timestamp;
    
    @Column(name = "audit_hash")
    private String auditHash; 
}