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

<<<<<<< HEAD
        // 3. Validação de Unicidade
=======
>>>>>>> 1d628f725aadaeeb6666b2f0266d411aed625f25
        if (voteRepository.existsByAssemblyIdAndUserId(assemblyId, userId)) {
            throw new RuntimeException("Você já votou nesta assembleia.");
        }

<<<<<<< HEAD
        // 4. Auditoria
=======
>>>>>>> 1d628f725aadaeeb6666b2f0266d411aed625f25
        String dadosBrutos = userId.toString() + assemblyId.toString() + opcao + LocalDateTime.now().toString();
        String hashAssinatura = DigestUtils.sha256Hex(dadosBrutos);

        Vote voto = new Vote();
        voto.setAssembly(assembly);
<<<<<<< HEAD
        voto.setUser(user);
        voto.setOpcaoEscolhida(opcao);
        voto.setAuditHash(hashAssinatura);
        
        // CORREÇÃO: Removemos setTimestamp. 
        // A BaseEntity vai definir o 'createdAt' automaticamente ao salvar.
=======
        voto.setUser(user); // Agora o método existe
        voto.setOpcaoEscolhida(opcao); // Agora o método existe
        voto.setTimestamp(LocalDateTime.now());
        voto.setAuditHash(hashAssinatura);
        voto.setTenant(assembly.getTenant()); // Importante manter o Tenant
>>>>>>> 1d628f725aadaeeb6666b2f0266d411aed625f25

        return voteRepository.save(voto);
    }
}