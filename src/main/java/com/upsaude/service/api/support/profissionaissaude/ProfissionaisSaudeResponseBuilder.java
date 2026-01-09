package com.upsaude.service.api.support.profissionaissaude;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.profissional.ProfissionaisSaudeResponse;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.mapper.profissional.ProfissionaisSaudeMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfissionaisSaudeResponseBuilder {

    private final ProfissionaisSaudeMapper profissionaisSaudeMapper;

    public ProfissionaisSaudeResponse build(ProfissionaisSaude profissional) {
        try {
            if (profissional != null) {
                try {
                    var sigtapOcupacao = profissional.getSigtapOcupacao();
                    if (sigtapOcupacao != null) {
                        Hibernate.initialize(sigtapOcupacao);
                        if (Hibernate.isInitialized(sigtapOcupacao)) {
                            log.debug("SigtapOcupacao inicializado para profissional ID: {}, ocupação ID: {}, nome: {}", 
                                profissional.getId(), 
                                sigtapOcupacao.getId(),
                                sigtapOcupacao.getNome());
                        } else {
                            log.warn("SigtapOcupacao não foi inicializado para profissional ID: {}", profissional.getId());
                        }
                    } else {
                        log.debug("Profissional ID: {} não possui sigtapOcupacao (null)", profissional.getId());
                    }
                } catch (org.hibernate.LazyInitializationException e) {
                    log.warn("Erro LazyInitialization ao acessar sigtapOcupacao para profissional ID: {}. Erro: {}", 
                        profissional.getId(), e.getMessage());
                }
            }
        } catch (Exception e) {
            log.warn("Erro ao inicializar sigtapOcupacao para profissional ID: {}. Erro: {}", 
                profissional != null ? profissional.getId() : "null", e.getMessage(), e);
        }
        return profissionaisSaudeMapper.toResponse(profissional);
    }
}
