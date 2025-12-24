package com.votzz.backend.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "votes")
@Data
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "assembly_id")
    private Assembly assembly; // DEVE se chamar 'assembly' para o mappedBy funcionar

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "opcao_escolhida")
    private String opcaoEscolhida;

    private LocalDateTime timestamp = LocalDateTime.now();
    
    @Column(name = "audit_hash")
    private String auditHash;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;
}