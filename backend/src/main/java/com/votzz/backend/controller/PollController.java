package com.votzz.backend.controller;

import com.votzz.backend.domain.Poll;
import com.votzz.backend.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/polls")
@CrossOrigin(origins = "http://localhost:5173") // Ajuste conforme a porta do seu React
public class PollController {

    @Autowired
    private PollRepository pollRepository;

    @GetMapping
    public List<Poll> getAll() {
        // O TenantSecurityFilter (se configurado com Hibernate Filter) 
        // já deve filtrar os dados do condomínio correto.
        return pollRepository.findAll();
    }

    @PostMapping
    public Poll create(@RequestBody Poll poll) {
        // O TenantInterceptor ou a lógica de serviço deve setar o tenant atual no objeto poll
        // poll.setTenant(currentUser.getTenant()); -> Lógica idealmente na camada de Service
        return pollRepository.save(poll);
    }
    
    @PostMapping("/{id}/vote")
    public ResponseEntity<?> vote(@PathVariable String id) {
        // Implementação futura da lógica de voto
        return ResponseEntity.ok().build();
    }
}