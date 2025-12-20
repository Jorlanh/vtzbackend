package com.votzz.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.votzz.backend.domain.Booking;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    List<Booking> findByUserId(String userId);
    
    // Query para verificar conflitos de horário
    @Query("SELECT b FROM Booking b WHERE b.areaId = :areaId AND b.bookingDate = :date AND b.status <> 'CANCELLED' AND b.status <> 'REJECTED'")
    List<Booking> findActiveBookingsByAreaAndDate(String areaId, LocalDate date);
}