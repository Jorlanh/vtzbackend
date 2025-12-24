package com.votzz.backend.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
// CORREÇÃO: Alterado de "booking" para "reservations" para alinhar com o SQL
@Table(name = "reservations") 
@Data
@EqualsAndHashCode(callSuper = true)
public class Booking extends BaseEntity {
    // O campo 'id' e 'tenantId' já vêm do BaseEntity como UUID. 
    
    @Column(name = "area_id")
    private UUID areaId;

    @Column(name = "user_id")
    private UUID userId;

    private String unit;
    
    @Column(name = "booking_date") // Garante o mapeamento snake_case do SQL
    private LocalDate bookingDate;
    
    @Column(name = "start_time")
    private String startTime;
    
    @Column(name = "end_time")
    private String endTime;
    
    private String status;
    
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "approved_by")
    private String approvedBy;
}