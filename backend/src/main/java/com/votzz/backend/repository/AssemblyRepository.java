package com.votzz.backend.repository;

import com.votzz.backend.domain.Assembly;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssemblyRepository extends JpaRepository<Assembly, UUID> {
<<<<<<< HEAD
    
    // Busca pelo Enum definido na Entidade
    List<Assembly> findByStatus(Assembly.StatusAssembly status);
    
=======
    List<Assembly> findByTenantId(UUID tenantId);
    Optional<Assembly> findByIdAndTenantId(UUID id, UUID tenantId);
>>>>>>> 1d628f725aadaeeb6666b2f0266d411aed625f25
}