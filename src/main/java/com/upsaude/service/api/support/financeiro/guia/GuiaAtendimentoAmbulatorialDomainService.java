package com.upsaude.service.api.support.financeiro.guia;

import com.upsaude.entity.financeiro.GuiaAtendimentoAmbulatorial;
import com.upsaude.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class GuiaAtendimentoAmbulatorialDomainService {

    public void aplicarDefaults(GuiaAtendimentoAmbulatorial entity) {
        if (entity.getStatus() == null) {
            entity.setStatus("RASCUNHO");
        }
        if ("EMITIDA".equalsIgnoreCase(entity.getStatus()) && entity.getEmitidaEm() == null) {
            entity.setEmitidaEm(OffsetDateTime.now());
        }
    }

    public void validarPodeInativar(GuiaAtendimentoAmbulatorial entity) {
        if (entity == null) throw new BadRequestException("Guia é obrigatória");
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar guia já inativa. ID: {}", entity.getId());
            throw new BadRequestException("Guia já está inativa");
        }
    }

    public void validarPodeDeletar(GuiaAtendimentoAmbulatorial entity) {
        if (entity == null) throw new BadRequestException("Guia é obrigatória");
    }
}

