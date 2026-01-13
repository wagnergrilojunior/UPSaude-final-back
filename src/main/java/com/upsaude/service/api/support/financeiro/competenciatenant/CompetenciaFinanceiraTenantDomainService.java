package com.upsaude.service.api.support.financeiro.competenciatenant;

import com.upsaude.entity.financeiro.CompetenciaFinanceiraTenant;
import com.upsaude.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompetenciaFinanceiraTenantDomainService {

    public void validarPodeInativar(CompetenciaFinanceiraTenant entity) {
        if (entity == null) throw new BadRequestException("Competência (tenant) é obrigatória");
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar competência (tenant) já inativa. ID: {}", entity.getId());
            throw new BadRequestException("Competência (tenant) já está inativa");
        }
    }

    public void validarPodeDeletar(CompetenciaFinanceiraTenant entity) {
        if (entity == null) throw new BadRequestException("Competência (tenant) é obrigatória");
    }
}

