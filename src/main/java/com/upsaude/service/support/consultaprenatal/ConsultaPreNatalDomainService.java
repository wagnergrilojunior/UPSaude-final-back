package com.upsaude.service.support.consultaprenatal;

import com.upsaude.entity.PreNatal;
import com.upsaude.repository.ConsultaPreNatalRepository;
import com.upsaude.repository.PreNatalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

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

