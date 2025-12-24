package com.votzz.backend.repository;

import com.votzz.backend.domain.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, UUID> {
    // Extende JpaRepository para fornecer métodos como findAll() e findById()
}