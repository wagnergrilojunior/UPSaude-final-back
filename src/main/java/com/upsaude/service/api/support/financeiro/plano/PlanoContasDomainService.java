package com.upsaude.service.api.support.financeiro.plano;

import com.upsaude.entity.financeiro.PlanoContas;
import com.upsaude.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanoContasDomainService {

    public void aplicarDefaults(PlanoContas entity) {
        if (entity.getPadrao() == null) {
            entity.setPadrao(false);
        }
        if (entity.getActive() == null) {
            entity.setActive(true);
        }
    }

    public void validarPodeInativar(PlanoContas entity) {
        if (entity == null) {
            throw new BadRequestException("Plano de contas é obrigatório");
        }
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar plano de contas já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Plano de contas já está inativo");
        }
    }

    public void validarPodeDeletar(PlanoContas entity) {
        if (entity == null) {
            throw new BadRequestException("Plano de contas é obrigatório");
        }
    }
}

