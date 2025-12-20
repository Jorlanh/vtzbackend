// Local: src/main/java/com/votzz/backend/domain/Announcement.java
package com.votzz.backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

@Entity
@Table(name = "announcements")
@Data
@EqualsAndHashCode(callSuper = true)
public class Announcement extends BaseEntity {
    private String title;
    private String content;
    private String priority; // NORMAL, URGENT
    private String targetType; // ALL, OWNERS
    private Boolean requiresConfirmation;
    private LocalDateTime date;
}