package com.upsaude.service.api.support.financeiro.contafinanceira;

import com.upsaude.entity.financeiro.ContaFinanceira;
import com.upsaude.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContaFinanceiraDomainService {

    public void aplicarDefaults(ContaFinanceira entity) {
        if (entity.getMoeda() == null) {
            entity.setMoeda("BRL");
        }
        if (entity.getActive() == null) {
            entity.setActive(true);
        }
    }

    public void validarPodeInativar(ContaFinanceira entity) {
        if (entity == null) throw new BadRequestException("Conta financeira é obrigatória");
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar conta financeira já inativa. ID: {}", entity.getId());
            throw new BadRequestException("Conta financeira já está inativa");
        }
    }

    public void validarPodeDeletar(ContaFinanceira entity) {
        if (entity == null) throw new BadRequestException("Conta financeira é obrigatória");
    }
}

