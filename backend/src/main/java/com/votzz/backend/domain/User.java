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

    // CORREÇÃO: Renomeado de 'name' para 'nome' para atender getNome()
    private String nome; 
    
    // CORREÇÃO: Adicionado campo CPF para atender getCpf()
    private String cpf;

    @Column(unique = true)
    private String email;

    private String password;

    private String role; // ADMIN, SYNDIC, RESIDENT

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;
}