package com.votzz.backend.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
@Table(name = "tenants")
// CORREÇÃO: Removemos 'extends BaseEntity' e o 'EqualsAndHashCode(callSuper)'
// pois Tenant é a raiz, não possui tenant_id.
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;
    private String cnpj;
    private String plano;
    private Boolean ativo;
}