package com.votzz.backend.controller;

import com.votzz.backend.domain.Booking;
import com.votzz.backend.domain.CommonArea;
import com.votzz.backend.dto.BookingRequest;
import com.votzz.backend.repository.BookingRepository;
import com.votzz.backend.repository.CommonAreaRepository;
import com.votzz.backend.service.FacilitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facilities")
@CrossOrigin(origins = "http://localhost:5173")
public class FacilitiesController {

    @Autowired private CommonAreaRepository areaRepository;
    @Autowired private BookingRepository bookingRepository;
    @Autowired private FacilitiesService facilitiesService;

    @GetMapping("/areas")
    public List<CommonArea> getAreas() {
        return areaRepository.findAll();
    }

    @GetMapping("/bookings")
    public List<Booking> getBookings() {
        return bookingRepository.findAll();
    }

    @PostMapping("/bookings")
    public Booking createBooking(@RequestBody BookingRequest request) {
        return facilitiesService.createBooking(request);
    }
    
    // Atualizar status (Aprovar/Rejeitar)
    @PatchMapping("/bookings/{id}/status")
    public Booking updateStatus(@PathVariable String id, @RequestBody String status) {
        Booking b = bookingRepository.findById(id).orElseThrow();
        b.setStatus(status); // Em produção, validar se o status é válido
        return bookingRepository.save(b);
    }
}