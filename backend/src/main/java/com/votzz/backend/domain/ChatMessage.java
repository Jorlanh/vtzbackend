package com.votzz.backend.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_message")
@Data
@EqualsAndHashCode(callSuper = true)
public class ChatMessage extends BaseEntity { 
    // Remova o campo private String id; se ele existir aqui, 
    // pois o BaseEntity já fornece o ID como UUID.

    @Column(name = "assembly_id")
    private UUID assemblyId;

    @Column(name = "user_id")
    private UUID userId;

    private String userName;
    private String content;
    private LocalDateTime timestamp;
}