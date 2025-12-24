package com.votzz.backend.service;

import com.votzz.backend.domain.*;
import com.votzz.backend.repository.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final AssemblyRepository assemblyRepository;
    private final UserRepository userRepository;

    @Transactional
    public Vote registrarVoto(UUID assemblyId, UUID userId, String opcao) {
        Assembly assembly = assemblyRepository.findById(assemblyId)
            .orElseThrow(() -> new RuntimeException("Assembleia não existe"));
        
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuário não existe"));

        if (LocalDateTime.now().isAfter(assembly.getDataFim())) {
            throw new RuntimeException("Votação encerrada!");
        }

        if (voteRepository.existsByAssemblyIdAndUserId(assemblyId, userId)) {
            throw new RuntimeException("Você já votou nesta assembleia.");
        }

        String dadosBrutos = userId.toString() + assemblyId.toString() + opcao + LocalDateTime.now().toString();
        String hashAssinatura = DigestUtils.sha256Hex(dadosBrutos);

        Vote voto = new Vote();
        voto.setAssembly(assembly);
        voto.setUser(user); // Agora o método existe
        voto.setOpcaoEscolhida(opcao); // Agora o método existe
        voto.setTimestamp(LocalDateTime.now());
        voto.setAuditHash(hashAssinatura);
        voto.setTenant(assembly.getTenant()); // Importante manter o Tenant

        return voteRepository.save(voto);
    }
}