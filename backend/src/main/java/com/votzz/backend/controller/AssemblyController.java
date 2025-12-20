package com.votzz.backend.controller;

import com.votzz.backend.domain.Assembly;
import com.votzz.backend.domain.Vote;
import com.votzz.backend.repository.AssemblyRepository;
import com.votzz.backend.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/assemblies")
@RequiredArgsConstructor // Injeta automaticamente os final fields (Substitui @Autowired)
@CrossOrigin(origins = "http://localhost:5173") // Permite seu Frontend Vite
public class AssemblyController {

    private final AssemblyRepository assemblyRepository;
    private final VoteService voteService;

    // Listar todas
    @GetMapping
    public List<Assembly> getAll() {
        return assemblyRepository.findAll();
    }

    // Criar Assembleia (Gera Link de Vídeo Automático)
    @PostMapping
    public ResponseEntity<Assembly> criarAssembleia(@RequestBody Assembly assembly) {
        if (assembly.getLinkVideoConferencia() == null || assembly.getLinkVideoConferencia().isEmpty()) {
            String salaId = UUID.randomUUID().toString();
            // Gera link único do Jitsi
            assembly.setLinkVideoConferencia("https://meet.jit.si/votzz-" + salaId);
        }
        return ResponseEntity.ok(assemblyRepository.save(assembly));
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Assembly> getById(@PathVariable UUID id) {
        return assemblyRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Votar
    @PostMapping("/{id}/vote")
    public ResponseEntity<Vote> votar(@PathVariable UUID id, 
                                      @RequestParam UUID userId, 
                                      @RequestParam String opcao) {
        return ResponseEntity.ok(voteService.registrarVoto(id, userId, opcao));
    }
}