package com.votzz.backend.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reservations") // Sincronizado com SQL
@Data
@EqualsAndHashCode(callSuper = true)
public class Booking extends BaseEntity {
    
    @Column(name = "area_id")
    private UUID areaId;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "unit")
    private String unit;
    
    @Column(name = "booking_date") 
    private LocalDate bookingDate;
    
    @Column(name = "start_time")
    private String startTime;
    
    @Column(name = "end_time")
    private String endTime;
    
    private String status;
    
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    
    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "approved_by")
    private String approvedBy;
}