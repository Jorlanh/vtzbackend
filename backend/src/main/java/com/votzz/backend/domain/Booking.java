package com.votzz.backend.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDate;

<<<<<<< HEAD
=======
@Entity
@Table(name = "reservations") // Sincronizado com SQL
>>>>>>> 1d628f725aadaeeb6666b2f0266d411aed625f25
@Data
@Entity
@Table(name = "reservations")
@EqualsAndHashCode(callSuper = true)
public class Booking extends BaseEntity {
<<<<<<< HEAD

    // Tenant (read-only)
    @ManyToOne
    @JoinColumn(name = "tenant_id", insertable = false, updatable = false)
    private Tenant tenant;
=======
    
    @Column(name = "area_id")
    private UUID areaId;
>>>>>>> 1d628f725aadaeeb6666b2f0266d411aed625f25

    // CORRETO: Mapeando 'area_id' para o objeto CommonArea
    @ManyToOne
    @JoinColumn(name = "area_id") 
    private CommonArea commonArea;

    // Se houver algum campo antigo como este abaixo, APAGUE-O:
    // @Column(name = "area_comum")
    // private String areaComum;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "unit")
    private String unit;
    
<<<<<<< HEAD
    @Column(name = "booking_date")
=======
    @Column(name = "booking_date") 
>>>>>>> 1d628f725aadaeeb6666b2f0266d411aed625f25
    private LocalDate bookingDate;
    
    @Column(name = "start_time")
    private String startTime;
    
    @Column(name = "end_time")
    private String endTime;
    
<<<<<<< HEAD
    private String status; 

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "asaas_payment_id")
    private String asaasPaymentId;
=======
    private String status;
    
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    
    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "approved_by")
    private String approvedBy;
>>>>>>> 1d628f725aadaeeb6666b2f0266d411aed625f25
}