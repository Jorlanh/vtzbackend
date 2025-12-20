package com.votzz.backend.repository;

import com.votzz.backend.domain.Assembly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AssemblyRepository extends JpaRepository<Assembly, UUID> {
    
    // Corrigido para buscar pelo Enum certo (StatusAssembly)
    List<Assembly> findByStatus(Assembly.StatusAssembly status);
    
    // Se precisar buscar datas (ex: assembleias abertas agora)
    // List<Assembly> findByDataFimAfter(LocalDateTime data);
}