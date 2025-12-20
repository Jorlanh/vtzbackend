package com.votzz.backend.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "booking")
@Data
@EqualsAndHashCode(callSuper = true)
public class Booking extends BaseEntity {
    // O campo 'id' e 'tenantId' já vêm do BaseEntity como UUID. 
    // NÃO declare "private String id" aqui.

    @Column(name = "area_id")
    private UUID areaId;

    @Column(name = "user_id")
    private UUID userId;

    private String unit;
    private LocalDate bookingDate;
    private String startTime;
    private String endTime;
    private String status;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
    private String approvedBy;
}