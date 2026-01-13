package com.upsaude.service.api.support.financeiro.lancamento;

import com.upsaude.entity.financeiro.LancamentoFinanceiro;
import com.upsaude.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LancamentoFinanceiroDomainService {

    public void aplicarDefaults(LancamentoFinanceiro entity) {
        if (entity.getIdempotencyKey() == null) {
            entity.setIdempotencyKey(UUID.randomUUID().toString());
        }
        if (entity.getTravado() == null) {
            entity.setTravado(false);
        }
    }

    public void validarImutabilidade(LancamentoFinanceiro entity) {
        if (entity != null && Boolean.TRUE.equals(entity.getTravado())) {
            throw new BadRequestException("Lançamento financeiro está travado e não pode ser alterado");
        }
    }

    public void validarPodeInativar(LancamentoFinanceiro entity) {
        if (entity == null) throw new BadRequestException("Lançamento é obrigatório");
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar lançamento já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Lançamento já está inativo");
        }
        validarImutabilidade(entity);
    }

    public void validarPodeDeletar(LancamentoFinanceiro entity) {
        if (entity == null) throw new BadRequestException("Lançamento é obrigatório");
        validarImutabilidade(entity);
    }
}

