package com.upsaude.service.api.support.financeiro.contacontabil;

import com.upsaude.entity.financeiro.ContaContabil;
import com.upsaude.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContaContabilDomainService {

    public void aplicarDefaults(ContaContabil entity) {
        if (entity.getAceitaLancamento() == null) {
            entity.setAceitaLancamento(false);
        }
    }

    public void validarPodeInativar(ContaContabil entity) {
        if (entity == null) throw new BadRequestException("Conta contábil é obrigatória");
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar conta contábil já inativa. ID: {}", entity.getId());
            throw new BadRequestException("Conta contábil já está inativa");
        }
    }

    public void validarPodeDeletar(ContaContabil entity) {
        if (entity == null) throw new BadRequestException("Conta contábil é obrigatória");
    }
}

