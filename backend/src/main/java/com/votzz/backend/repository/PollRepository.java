package com.votzz.backend.repository;

import com.votzz.backend.domain.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PollRepository extends JpaRepository<Poll, UUID> {
    // O JpaRepository já fornece automaticamente métodos como:
    // findAll() -> SELECT * FROM polls
    // save(Poll p) -> INSERT/UPDATE
    // findById(UUID id) -> SELECT * FROM polls WHERE id = ?
    
    // Se você estiver usando isolamento de tenant via coluna 'tenant_id' e 
    // não estiver usando um Filtro Global do Hibernate, pode precisar de:
    // List<Poll> findAllByTenantId(UUID tenantId);
}