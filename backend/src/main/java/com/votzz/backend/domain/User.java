package com.votzz.backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "users")
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
    private Tenant tenant;
}