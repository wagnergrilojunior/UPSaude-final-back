package com.upsaude.service.api.support.financeiro.credito;

import com.upsaude.entity.financeiro.CreditoOrcamentario;
import com.upsaude.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreditoOrcamentarioDomainService {

    public void aplicarDefaults(CreditoOrcamentario entity) {
        if (entity.getIdempotencyKey() == null) {
            entity.setIdempotencyKey(UUID.randomUUID().toString());
        }
    }

    public void validarPodeInativar(CreditoOrcamentario entity) {
        if (entity == null) throw new BadRequestException("Crédito orçamentário é obrigatório");
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar crédito já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Crédito orçamentário já está inativo");
        }
    }

    public void validarPodeDeletar(CreditoOrcamentario entity) {
        if (entity == null) throw new BadRequestException("Crédito orçamentário é obrigatório");
    }
}

