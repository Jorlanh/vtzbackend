package com.votzz.backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "users")
<<<<<<< HEAD
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String cpf;
    
    private String unidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; 

    private String whatsapp;

    // --- CORREÇÃO DO ERRO ---
    // Adicionado insertable = false, updatable = false
    // Isso diz ao Hibernate: "Use o tenantId da BaseEntity para salvar, e use este campo apenas para ler o objeto Tenant"
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", insertable = false, updatable = false)
=======
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // Sincronizado com o DDL e o formulário de cadastro
    private String nome; 
    
    private String cpf;

    @Column(unique = true)
    private String email;

    private String password;

    private String unidade;

    private String role; // ADMIN, SYNDIC, RESIDENT

    @ManyToOne
    @JoinColumn(name = "tenant_id")
>>>>>>> 1d628f725aadaeeb6666b2f0266d411aed625f25
    private Tenant tenant;
}