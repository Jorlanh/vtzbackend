package com.votzz.backend.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "chat_message")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "assembly_id")
    private String assemblyId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_name")
    private String userName;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime timestamp = LocalDateTime.now();
}