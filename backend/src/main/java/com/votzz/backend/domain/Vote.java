package com.votzz.backend.domain;

import jakarta.persistence.*;
import lombok.Data;
<<<<<<< HEAD
import lombok.EqualsAndHashCode;
=======
import java.time.LocalDateTime;
import java.util.UUID;
>>>>>>> 1d628f725aadaeeb6666b2f0266d411aed625f25

@Data
@Entity
@Table(name = "votes")
<<<<<<< HEAD
@EqualsAndHashCode(callSuper = true)
public class Vote extends BaseEntity { // Herda createdAt da BaseEntity

    @ManyToOne
    @JoinColumn(name = "tenant_id", insertable = false, updatable = false)
    private Tenant tenant;
=======
@Data
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
>>>>>>> 1d628f725aadaeeb6666b2f0266d411aed625f25

    @ManyToOne
    @JoinColumn(name = "assembly_id")
    private Assembly assembly; // DEVE se chamar 'assembly' para o mappedBy funcionar

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

<<<<<<< HEAD
    @Column(name = "opcao_escolhida", nullable = false)
    private String opcaoEscolhida;

    @Column(name = "audit_hash")
    private String auditHash;
=======
    @Column(name = "opcao_escolhida")
    private String opcaoEscolhida;

    private LocalDateTime timestamp = LocalDateTime.now();
    
    @Column(name = "audit_hash")
    private String auditHash;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;
>>>>>>> 1d628f725aadaeeb6666b2f0266d411aed625f25
}