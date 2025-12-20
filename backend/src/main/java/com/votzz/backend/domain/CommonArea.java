package com.votzz.backend.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "common_area")
public class CommonArea {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private String type; // PARTY_ROOM, BBQ, etc.
    private Integer capacity;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(columnDefinition = "TEXT")
    private String rules;
    
    @Column(name = "image_url")
    private String imageUrl;
    
    private BigDecimal price;
    
    @Column(name = "requires_approval")
    private Boolean requiresApproval;
    
    @Column(name = "min_time_block")
    private Integer minTimeBlock;
    
    @Column(name = "max_time_block")
    private Integer maxTimeBlock;
    
    @Column(name = "cleaning_interval")
    private Integer cleaningInterval;
    
    @Column(name = "open_time")
    private String openTime;
    
    @Column(name = "close_time")
    private String closeTime;
}