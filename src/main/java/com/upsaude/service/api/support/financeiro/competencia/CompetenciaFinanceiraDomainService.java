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

        if (entity.isFechada()) {
            log.warn("Tentativa de inativar competência fechada. ID: {}", entity.getId());
            throw new BadRequestException("Não é possível inativar uma competência fechada");
        }
    }

    public void validarPodeDeletar(CompetenciaFinanceira entity) {
        if (entity == null) {
            throw new BadRequestException("Competência financeira é obrigatória");
        }

        if (entity.isFechada()) {
            log.warn("Tentativa de deletar competência fechada. ID: {}", entity.getId());
            throw new BadRequestException("Não é possível deletar uma competência fechada. É necessário reabrir primeiro.");
        }
    }

    public void validarPodeFechar(CompetenciaFinanceira entity) {
        if (entity == null) {
            throw new BadRequestException("Competência financeira é obrigatória");
        }
        if (entity.isFechada()) {
            log.warn("Tentativa de fechar competência já fechada. ID: {}", entity.getId());
            throw new BadRequestException("Competência financeira já está fechada");
        }
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de fechar competência inativa. ID: {}", entity.getId());
            throw new BadRequestException("Não é possível fechar uma competência inativa");
        }
    }

    public void validarPodeReabrir(CompetenciaFinanceira entity) {
        if (entity == null) {
            throw new BadRequestException("Competência financeira é obrigatória");
        }
        if (entity.isAberta()) {
            log.warn("Tentativa de reabrir competência já aberta. ID: {}", entity.getId());
            throw new BadRequestException("Competência financeira já está aberta");
        }
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de reabrir competência inativa. ID: {}", entity.getId());
            throw new BadRequestException("Não é possível reabrir uma competência inativa");
        }
    }
}

