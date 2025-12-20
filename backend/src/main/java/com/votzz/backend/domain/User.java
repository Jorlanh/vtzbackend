package com.votzz.backend.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private String unidade;
    
    private String role; 

    // CORREÇÃO AQUI: Adicionado insertable=false, updatable=false
    // Isso diz ao Hibernate: "Use o tenantId do BaseEntity para salvar, 
    // e use este campo apenas para ler os dados do Condomínio"
    @ManyToOne
    @JoinColumn(name = "tenant_id", insertable = false, updatable = false)
    private Tenant tenant;
}