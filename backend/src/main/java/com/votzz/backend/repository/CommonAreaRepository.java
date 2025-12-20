package com.votzz.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.votzz.backend.domain.CommonArea;

@Repository
public interface CommonAreaRepository extends JpaRepository<CommonArea, String> {}