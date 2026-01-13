package com.upsaude.service.api.support.financeiro.estorno;

import com.upsaude.entity.financeiro.EstornoFinanceiro;
import com.upsaude.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EstornoFinanceiroDomainService {

    public void validarPodeInativar(EstornoFinanceiro entity) {
        if (entity == null) throw new BadRequestException("Estorno é obrigatório");
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar estorno já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Estorno já está inativo");
        }
    }

    public void validarPodeDeletar(EstornoFinanceiro entity) {
        if (entity == null) throw new BadRequestException("Estorno é obrigatório");
    }
}

