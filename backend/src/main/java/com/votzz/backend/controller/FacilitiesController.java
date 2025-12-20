package com.votzz.backend.controller;

import com.votzz.backend.domain.CommonArea;
import com.votzz.backend.repository.CommonAreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/facilities")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class FacilitiesController {

    private final CommonAreaRepository areaRepository;

    @GetMapping("/areas")
    public List<CommonArea> getAreas() {
        return areaRepository.findAll();
    }

    @PostMapping("/areas")
    public CommonArea createArea(@RequestBody CommonArea area) {
        // Garante que a área tenha um ID se não for enviado
        if (area.getId() == null) {
            area.setId(java.util.UUID.randomUUID().toString());
        }
        return areaRepository.save(area);
    }

    @PatchMapping("/areas/{id}")
    // Alterado de UUID para String para bater com o Repository
    public ResponseEntity<CommonArea> updateArea(@PathVariable String id, @RequestBody CommonArea areaDetails) {
        return areaRepository.findById(id).map(area -> {
            if (areaDetails.getName() != null) area.setName(areaDetails.getName());
            if (areaDetails.getCapacity() != null) area.setCapacity(areaDetails.getCapacity());
            if (areaDetails.getPrice() != null) area.setPrice(areaDetails.getPrice());
            if (areaDetails.getDescription() != null) area.setDescription(areaDetails.getDescription());
            if (areaDetails.getImageUrl() != null) area.setImageUrl(areaDetails.getImageUrl());
            return ResponseEntity.ok(areaRepository.save(area));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/areas/{id}")
    // Alterado de UUID para String para bater com o Repository
    public ResponseEntity<Void> deleteArea(@PathVariable String id) {
        areaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}