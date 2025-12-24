package com.votzz.backend.controller;

import com.votzz.backend.domain.Assembly;
import com.votzz.backend.domain.User;
import com.votzz.backend.repository.AssemblyRepository;
import com.votzz.backend.service.FileStorageService; // Assumindo que você tem ou criará um serviço simples
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/assemblies")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Ajuste para produção
public class AssemblyController {

    private final AssemblyRepository assemblyRepository;
    // private final FileStorageService fileStorageService; // Descomente se tiver implementação de S3/Local

    // LISTAR (Com filtro de arquivadas se necessário, ou filtrar no front)
    @GetMapping
    public List<Assembly> getAll(@AuthenticationPrincipal User user) {
        return assemblyRepository.findByTenantId(user.getTenant().getId());
    }

    // CRIAR COM UPLOAD (Multipart)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Assembly> createAssembly(
            @RequestPart("data") Assembly assembly,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @AuthenticationPrincipal User user) {
        
        assembly.setTenant(user.getTenant());
        assembly.setStatus(Assembly.StatusAssembly.AGENDADA);

        // Tratamento do Link do YouTube
        if (assembly.getLinkVideoConferencia() != null) {
            assembly.setLinkVideoConferencia(extractYoutubeId(assembly.getLinkVideoConferencia()));
        }

        // Tratamento simplificado de Upload (Exemplo)
        if (file != null && !file.isEmpty()) {
            // Lógica de upload aqui (S3 ou Disco Local)
            // String url = fileStorageService.store(file);
            // assembly.setAttachmentUrl(url);
            System.out.println("Arquivo recebido: " + file.getOriginalFilename());
        }

        return ResponseEntity.ok(assemblyRepository.save(assembly));
    }

    // DOWNLOAD DE AUDITORIA DE VOTOS (CSV)
    @GetMapping("/{id}/votes/csv")
    public ResponseEntity<byte[]> downloadVotes(@PathVariable UUID id) {
        Assembly assembly = assemblyRepository.findById(id).orElseThrow();
        
        // Cabeçalho do CSV
        StringBuilder csv = new StringBuilder("Usuario;Unidade;Voto;Data\n");
        
        // Lógica fictícia - Substitua pelo acesso real aos votos
        // assembly.getVotes().forEach(v -> csv.append(...));
        
        byte[] bytes = csv.toString().getBytes(StandardCharsets.UTF_8);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=votos_" + id + ".csv")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(bytes);
    }

    // Método auxiliar para extrair ID do YouTube
    private String extractYoutubeId(String url) {
        String pattern = "(?<=watch\\?v=|/videos/|embed/|youtu.be/|/v/|/e/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%2F|youtu.be%2F|%2Fv%2F)[^#&?\\n]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(url);
        return (matcher.find()) ? matcher.group() : url;
    }
}