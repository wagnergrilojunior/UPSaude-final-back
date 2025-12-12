package com.upsaude.service.support.profissionaissaude;

import com.upsaude.api.request.ProfissionaisSaudeRequest;
import com.upsaude.entity.EspecialidadesMedicas;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.EspecialidadesMedicasRepository;
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
public class ProfissionaisSaudeAssociacoesHandler {

    private final EspecialidadesMedicasRepository especialidadesMedicasRepository;

    public ProfissionaisSaude processarEspecialidades(ProfissionaisSaude profissional, ProfissionaisSaudeRequest request) {
        if (request.getEspecialidades() != null && !request.getEspecialidades().isEmpty()) {
            log.debug("Processando {} especialidade(s) para o profissional", request.getEspecialidades().size());
            List<EspecialidadesMedicas> especialidades = new ArrayList<>();

            Set<UUID> especialidadesIdsUnicos = new LinkedHashSet<>(request.getEspecialidades());

            if (especialidadesIdsUnicos.size() != request.getEspecialidades().size()) {
                log.warn("Lista de especialidades contém IDs duplicados. Removendo duplicatas.");
            }

            for (UUID especialidadeId : especialidadesIdsUnicos) {
                if (especialidadeId == null) {
                    log.warn("ID de especialidade nulo encontrado na lista. Ignorando.");
                    continue;
                }

                EspecialidadesMedicas especialidade = especialidadesMedicasRepository
                        .findById(especialidadeId)
                        .orElseThrow(() -> new NotFoundException(
                                "Especialidade médica não encontrada com ID: " + especialidadeId));
                especialidades.add(especialidade);
                log.debug("Especialidade {} associada ao profissional", especialidadeId);
            }

            profissional.setEspecialidades(especialidades);
            log.debug("{} especialidade(s) associada(s) ao profissional com sucesso", especialidades.size());
        } else {
            profissional.setEspecialidades(new ArrayList<>());
            log.debug("Nenhuma especialidade fornecida. Lista de especialidades será limpa.");
        }

        return profissional;
    }
}
