package com.upsaude.service.api.support.financeiro.centrocusto;

import com.upsaude.entity.financeiro.CentroCusto;
import com.upsaude.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CentroCustoDomainService {

    public void aplicarDefaults(CentroCusto entity) {
        if (entity.getActive() == null) {
            entity.setActive(true);
        }
    }

    public void validarPodeInativar(CentroCusto entity) {
        if (entity == null) throw new BadRequestException("Centro de custo é obrigatório");
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar centro de custo já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Centro de custo já está inativo");
        }
    }

    public void validarPodeDeletar(CentroCusto entity) {
        if (entity == null) throw new BadRequestException("Centro de custo é obrigatório");
    }
}

