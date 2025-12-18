package com.upsaude.service.support.consultaprenatal;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.entity.saude_publica.planejamento.PreNatal;
import com.upsaude.repository.clinica.atendimento.ConsultaPreNatalRepository;
import com.upsaude.repository.saude_publica.planejamento.PreNatalRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultaPreNatalDomainService {

    private final ConsultaPreNatalRepository consultaRepository;
    private final PreNatalRepository preNatalRepository;

    public void recalcularNumeroConsultas(PreNatal preNatal, UUID tenantId) {
        if (preNatal == null || preNatal.getId() == null) return;

        long count = consultaRepository.countByPreNatalIdAndTenantId(preNatal.getId(), tenantId);
        preNatal.setNumeroConsultasPreNatal((int) count);
        preNatalRepository.save(Objects.requireNonNull(preNatal));
        log.debug("Número de consultas do pré-natal recalculado. preNatalId: {}, count: {}", preNatal.getId(), count);
    }
}

