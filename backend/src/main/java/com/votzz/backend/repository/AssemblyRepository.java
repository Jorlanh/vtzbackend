package com.votzz.backend.repository;

import com.votzz.backend.domain.Assembly;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssemblyRepository extends JpaRepository<Assembly, UUID> {
    List<Assembly> findByTenantId(UUID tenantId);
    Optional<Assembly> findByIdAndTenantId(UUID id, UUID tenantId);
}