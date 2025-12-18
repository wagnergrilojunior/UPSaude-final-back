package com.upsaude.service.support.consultapuericultura;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.entity.saude_publica.puericultura.Puericultura;
import com.upsaude.repository.clinica.atendimento.ConsultaPuericulturaRepository;
import com.upsaude.repository.saude_publica.puericultura.PuericulturaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultaPuericulturaDomainService {

    private final ConsultaPuericulturaRepository consultaRepository;
    private final PuericulturaRepository puericulturaRepository;

    public void recalcularNumeroConsultas(Puericultura puericultura, UUID tenantId) {
        if (puericultura == null || puericultura.getId() == null) return;

        long count = consultaRepository.countByPuericulturaIdAndTenantId(puericultura.getId(), tenantId);
        // Nota: A entidade Puericultura não possui campo para número de consultas
        // Se necessário, adicionar campo numeroConsultas na entidade
        log.debug("Número de consultas da puericultura recalculado. puericulturaId: {}, count: {}", puericultura.getId(), count);
    }
}
