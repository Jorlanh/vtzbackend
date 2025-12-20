package com.votzz.backend.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "reservations") // <--- PREVENÇÃO DE ERRO
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "tenant_id")
    private UUID tenantId;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "area_comum")
    private String areaComum;

    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;

    @Column(name = "data_fim")
    private LocalDateTime dataFim;

    private String status;
}