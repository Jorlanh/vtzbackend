package com.votzz.backend.controller;

import com.votzz.backend.domain.Assembly;
import com.votzz.backend.domain.User;
import com.votzz.backend.repository.AssemblyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/assemblies")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AssemblyController {

    private final AssemblyRepository assemblyRepository;

    @GetMapping
    public List<Assembly> getAll(@AuthenticationPrincipal User user) {
        if (user.getTenant() == null) {
            throw new RuntimeException("Usuário sem condomínio vinculado");
        }
        return assemblyRepository.findByTenantId(user.getTenant().getId());
    }

    @PostMapping
    public ResponseEntity<Assembly> criarAssembleia(@RequestBody Assembly assembly, @AuthenticationPrincipal User user) {
        if (user.getTenant() == null) {
            return ResponseEntity.badRequest().build();
        }
        
        assembly.setTenant(user.getTenant());

        if (assembly.getLinkVideoConferencia() != null && !assembly.getLinkVideoConferencia().isEmpty()) {
            String youtubeId = extractYoutubeId(assembly.getLinkVideoConferencia());
            assembly.setLinkVideoConferencia(youtubeId);
        }
        
        // Define status padrão se nulo
        if (assembly.getStatus() == null) {
            assembly.setStatus(Assembly.StatusAssembly.AGENDADA);
        }
        
        return ResponseEntity.ok(assemblyRepository.save(assembly));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assembly> getById(@PathVariable String id, @AuthenticationPrincipal User user) {
        try {
            UUID uuid = UUID.fromString(id);
            if (user.getTenant() == null) return ResponseEntity.notFound().build();

            return assemblyRepository.findByIdAndTenantId(uuid, user.getTenant().getId())
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private String extractYoutubeId(String url) {
        String pattern = "(?<=watch\\?v=|/videos/|embed/|youtu.be/|/v/|/e/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%2F|youtu.be%2F|%2Fv%2F)[^#&?\\n]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(url);
        return (matcher.find()) ? matcher.group() : url;
    }
}