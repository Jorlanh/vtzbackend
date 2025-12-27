package com.votzz.backend.controller;

import com.votzz.backend.domain.Assembly;
import com.votzz.backend.domain.User;
import com.votzz.backend.repository.AssemblyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/assemblies")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AssemblyController {

    private final AssemblyRepository assemblyRepository;

    @GetMapping
    public List<Assembly> getAll(@AuthenticationPrincipal User user) {
        return assemblyRepository.findByTenantId(user.getTenant().getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assembly> getById(@PathVariable UUID id) {
        return assemblyRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Assembly> createAssembly(
            @RequestPart("data") Assembly assembly,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @AuthenticationPrincipal User user) {
        
        assembly.setTenant(user.getTenant());
        assembly.setStatus("AGENDADA");

        if (assembly.getLinkVideoConferencia() != null) {
            assembly.setLinkVideoConferencia(extractYoutubeId(assembly.getLinkVideoConferencia()));
        }

        if (file != null && !file.isEmpty()) {
            System.out.println("Arquivo recebido: " + file.getOriginalFilename());
        }

        return ResponseEntity.ok(assemblyRepository.save(assembly));
    }

    @GetMapping("/{id}/votes/csv")
    public ResponseEntity<byte[]> downloadVotes(@PathVariable UUID id) {
        Assembly assembly = assemblyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assembleia não encontrada"));
        
        StringBuilder csv = new StringBuilder("Usuario;Unidade;Voto;Data\n");
        
        if (assembly.getVotes() != null) {
            assembly.getVotes().forEach(v -> {
                csv.append(v.getUser().getNome()).append(";")
                   // Agora este método funcionará corretamente
                   .append(v.getUser().getUnidade() != null ? v.getUser().getUnidade() : "N/A").append(";")
                   .append(v.getOpcaoEscolhida()).append(";")
                   .append(v.getTimestamp()).append("\n");
            });
        }
        
        byte[] bytes = csv.toString().getBytes(StandardCharsets.UTF_8);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=votos_" + id + ".csv")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(bytes);
    }

    private String extractYoutubeId(String url) {
        if (url == null || url.isBlank()) return url;
        String pattern = "(?<=watch\\?v=|/videos/|embed/|youtu.be/|/v/|/e/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%2F|youtu.be%2F|%2Fv%2F)[^#&?\\n]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(url);
        return (matcher.find()) ? matcher.group() : url;
    }
}