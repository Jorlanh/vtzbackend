package com.votzz.backend.repository;

import com.votzz.backend.domain.CondoFinancial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CondoFinancialRepository extends JpaRepository<CondoFinancial, String> {
    CondoFinancial findFirstByOrderByLastUpdateDesc();
}