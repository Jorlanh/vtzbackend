package com.votzz.backend.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

import com.votzz.backend.core.tenant.TenantContext;

@MappedSuperclass
@Data
public abstract class BaseEntity {
    @Column(name = "tenant_id", updatable = false)
    private UUID tenantId;

    @PrePersist
    public void prePersist() {
        if (this.tenantId == null) {
            this.tenantId = TenantContext.getTenant();
        }
    }
}