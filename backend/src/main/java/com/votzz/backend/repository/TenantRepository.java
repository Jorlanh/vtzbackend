package com.votzz.backend.repository;

import com.votzz.backend.domain.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
=======
import org.springframework.stereotype.Repository;
>>>>>>> 1d628f725aadaeeb6666b2f0266d411aed625f25
import java.util.UUID;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, UUID> {
<<<<<<< HEAD
    
    // Conta quantos condomínios estão ativos
    long countByAtivoTrue();

    // Busca condomínios ativos para cálculo de MRR
    List<Tenant> findByAtivoTrue();
=======
    // Extende JpaRepository para fornecer métodos como findAll() e findById()
>>>>>>> 1d628f725aadaeeb6666b2f0266d411aed625f25
}