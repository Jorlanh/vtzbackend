package com.votzz.backend.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

@Data
@Entity
// IMPORTANTE: Essa linha força o Java a procurar a tabela no PLURAL
@Table(name = "announcements") 
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "tenant_id")
    private UUID tenantId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String priority;

    @Column(name = "target_type")
    private String targetType;

    @Column(name = "requires_confirmation")
    private Boolean requiresConfirmation;

    @CreationTimestamp
    @Column(name = "date", updatable = false)
    private LocalDateTime date; // Nome da coluna no banco é 'date'

    @Transient
    private List<String> readBy;
}