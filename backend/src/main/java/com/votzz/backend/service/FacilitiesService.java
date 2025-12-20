package com.votzz.backend.service;

import com.votzz.backend.domain.Booking;
import com.votzz.backend.domain.CommonArea;
import com.votzz.backend.dto.BookingRequest;
import com.votzz.backend.repository.BookingRepository;
import com.votzz.backend.repository.CommonAreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FacilitiesService {

    private final BookingRepository bookingRepository;
    private final CommonAreaRepository areaRepository;

    public Booking createBooking(BookingRequest req) {
        // Converte as Strings vindas do DTO/Frontend para UUID
        UUID areaUuid = UUID.fromString(req.areaId());
        UUID userUuid = UUID.fromString(req.userId());

        // 1. Validar Conflitos
        List<Booking> existing = bookingRepository.findActiveBookingsByAreaAndDate(areaUuid, req.date());
        
        LocalTime newStart = LocalTime.parse(req.startTime());
        LocalTime newEnd = LocalTime.parse(req.endTime());

        boolean hasConflict = existing.stream().anyMatch(b -> {
            LocalTime bStart = LocalTime.parse(b.getStartTime());
            LocalTime bEnd = LocalTime.parse(b.getEndTime());
            return (newStart.isBefore(bEnd) && newStart.isAfter(bStart)) ||
                   (newEnd.isAfter(bStart) && newEnd.isBefore(bEnd)) ||
                   (newStart.equals(bStart));
        });

        if (hasConflict) {
            throw new RuntimeException("Horário indisponível. Conflito com outra reserva.");
        }

        // 2. Buscar Área (Agora o repositório aceita UUID corretamente)
        CommonArea area = areaRepository.findById(areaUuid)
                .orElseThrow(() -> new RuntimeException("Área comum não encontrada"));

        // 3. Salvar
        Booking booking = new Booking();
        booking.setAreaId(areaUuid);
        booking.setUserId(userUuid);
        booking.setUnit(req.unit());
        booking.setBookingDate(req.date());
        booking.setStartTime(req.startTime());
        booking.setEndTime(req.endTime());
        booking.setTotalPrice(area.getPrice());
        
        boolean needsApproval = area.getRequiresApproval() != null && area.getRequiresApproval();
        booking.setStatus(needsApproval ? "PENDING" : "APPROVED");
        
        return bookingRepository.save(booking);
    }
}