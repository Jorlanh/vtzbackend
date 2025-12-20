package com.votzz.backend.service;

import com.votzz.backend.domain.Booking;
import com.votzz.backend.domain.CommonArea;
import com.votzz.backend.dto.BookingRequest;
import com.votzz.backend.repository.BookingRepository;
import com.votzz.backend.repository.CommonAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class FacilitiesService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private CommonAreaRepository areaRepository;

    public Booking createBooking(BookingRequest req) {
        // 1. Validar Conflitos
        List<Booking> existing = bookingRepository.findActiveBookingsByAreaAndDate(req.areaId(), req.date());
        
        LocalTime newStart = LocalTime.parse(req.startTime());
        LocalTime newEnd = LocalTime.parse(req.endTime());

        boolean hasConflict = existing.stream().anyMatch(b -> {
            LocalTime bStart = LocalTime.parse(b.getStartTime());
            LocalTime bEnd = LocalTime.parse(b.getEndTime());
            // Lógica de sobreposição de horários
            return (newStart.isBefore(bEnd) && newStart.isAfter(bStart)) ||
                   (newEnd.isAfter(bStart) && newEnd.isBefore(bEnd)) ||
                   (newStart.equals(bStart));
        });

        if (hasConflict) {
            throw new RuntimeException("Horário indisponível. Conflito com outra reserva.");
        }

        // 2. Buscar Área para pegar preço e regras
        CommonArea area = areaRepository.findById(req.areaId()).orElseThrow();

        // 3. Salvar
        Booking booking = new Booking();
        booking.setAreaId(req.areaId());
        booking.setUserId(req.userId());
        booking.setUnit(req.unit());
        booking.setBookingDate(req.date());
        booking.setStartTime(req.startTime());
        booking.setEndTime(req.endTime());
        booking.setTotalPrice(area.getPrice());
        booking.setStatus(area.getRequiresApproval() ? "PENDING" : "APPROVED");
        
        return bookingRepository.save(booking);
    }
}