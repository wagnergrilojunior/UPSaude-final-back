package com.upsaude.service.support.acaopromocaoprevencao;

import com.upsaude.api.request.educacao.AcaoPromocaoPrevencaoRequest;
import com.upsaude.entity.educacao.AcaoPromocaoPrevencao;
import com.upsaude.entity.equipe.EquipeSaude;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.profissional.equipe.EquipeSaudeRepository;
import com.upsaude.repository.profissional.ProfissionaisSaudeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AcaoPromocaoPrevencaoRelacionamentosHandler {

    private final ProfissionaisSaudeRepository profissionaisSaudeRepository;
    private final EquipeSaudeRepository equipeSaudeRepository;

    public AcaoPromocaoPrevencao processarRelacionamentos(AcaoPromocaoPrevencao acao, AcaoPromocaoPrevencaoRequest request, UUID tenantId) {
        ProfissionaisSaude responsavel = profissionaisSaudeRepository.findByIdAndTenant(request.getProfissionalResponsavel(), tenantId)
                .orElseThrow(() -> new NotFoundException("Profissional responsável não encontrado com ID: " + request.getProfissionalResponsavel()));
        acao.setProfissionalResponsavel(responsavel);

        if (request.getEquipeSaude() != null) {
            EquipeSaude equipe = equipeSaudeRepository.findByIdAndTenant(request.getEquipeSaude(), tenantId)
                    .orElseThrow(() -> new NotFoundException("Equipe de saúde não encontrada com ID: " + request.getEquipeSaude()));
            acao.setEquipeSaude(equipe);
        } else {
            acao.setEquipeSaude(null);
        }

        if (request.getProfissionaisParticipantes() != null && !request.getProfissionaisParticipantes().isEmpty()) {
            Set<UUID> unicos = new LinkedHashSet<>(request.getProfissionaisParticipantes());
            List<ProfissionaisSaude> participantes = new ArrayList<>();
            for (UUID pid : unicos) {
                if (pid == null) continue;
                ProfissionaisSaude p = profissionaisSaudeRepository.findByIdAndTenant(pid, tenantId)
                        .orElseThrow(() -> new NotFoundException("Profissional participante não encontrado com ID: " + pid));
                participantes.add(p);
            }
            acao.setProfissionaisParticipantes(participantes);
        } else {
            acao.setProfissionaisParticipantes(new ArrayList<>());
        }

        // estabelecer estabelecimento/tenant a partir do profissional responsável
        acao.setTenant(responsavel.getTenant());
        acao.setEstabelecimento(responsavel.getEstabelecimento());

        return acao;
    }
}
