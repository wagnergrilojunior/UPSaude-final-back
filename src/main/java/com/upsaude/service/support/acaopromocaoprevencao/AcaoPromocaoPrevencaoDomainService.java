package com.upsaude.service.support.acaopromocaoprevencao;

import com.upsaude.entity.AcaoPromocaoPrevencao;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AcaoPromocaoPrevencaoDomainService {

    public void validarPodeInativar(AcaoPromocaoPrevencao acao) {
        if (Boolean.FALSE.equals(acao.getActive())) {
            log.warn("Tentativa de inativar ação já inativa. ID: {}", acao.getId());
            throw new BadRequestException("Ação de promoção e prevenção já está inativa");
        }
    }
}
