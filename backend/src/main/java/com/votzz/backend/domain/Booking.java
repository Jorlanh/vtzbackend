package com.votzz.backend.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "area_id")
    private String areaId;

    @Column(name = "user_id")
    private String userId;

    private String unit;

    @Column(name = "booking_date")
    private LocalDate bookingDate;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;

    // PENDING, APPROVED, REJECTED, CANCELLED
    private String status;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "approved_by")
    private String approvedBy;
}