package com.upsaude.service.api.support.financeiro.competencia;

import com.upsaude.entity.financeiro.CompetenciaFinanceira;
import com.upsaude.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompetenciaFinanceiraDomainService {

    public void validarPodeInativar(CompetenciaFinanceira entity) {
        if (entity == null) {
            throw new BadRequestException("Competência financeira é obrigatória");
        }
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar competência já inativa. ID: {}", entity.getId());
            throw new BadRequestException("Competência financeira já está inativa");
        }
    }

    public void validarPodeDeletar(CompetenciaFinanceira entity) {
        if (entity == null) {
            throw new BadRequestException("Competência financeira é obrigatória");
        }
    }
}

