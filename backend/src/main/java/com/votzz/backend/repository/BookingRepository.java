package com.votzz.backend.repository;

import com.votzz.backend.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {

    @Query("SELECT b FROM Booking b WHERE b.areaId = :areaId AND b.bookingDate = :date AND b.status != 'CANCELLED'")
    List<Booking> findActiveBookingsByAreaAndDate(UUID areaId, LocalDate date);
}