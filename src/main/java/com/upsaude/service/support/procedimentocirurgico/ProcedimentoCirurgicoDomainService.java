package com.upsaude.service.support.procedimentocirurgico;

import com.upsaude.entity.ProcedimentoCirurgico;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class ProcedimentoCirurgicoDomainService {

    public void calcularValorTotalSeAplicavel(ProcedimentoCirurgico entity) {
        if (entity == null) return;
        if (entity.getValorUnitario() != null && entity.getQuantidade() != null) {
            entity.setValorTotal(entity.getValorUnitario().multiply(BigDecimal.valueOf(entity.getQuantidade())));
        }
    }

    public void validarPodeInativar(ProcedimentoCirurgico entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar procedimento cirúrgico já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Procedimento cirúrgico já está inativo");
        }
    }
}

