package com.upsaude.service.api.support.farmacia;

import com.upsaude.entity.farmacia.Receita;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
public class ReceitaDomainService {

    public void validarPodeInativar(Receita receita) {
        if (Boolean.FALSE.equals(receita.getActive())) {
            log.warn("Tentativa de inativar receita já inativa. ID: {}", receita.getId());
            throw new BadRequestException("Receita já está inativa");
        }
    }

    public void validarPodeDeletar(Receita receita) {
        log.debug("Validando se receita pode ser deletada. ID: {}", receita.getId());
    }

    public void validarReceitaValida(Receita receita) {
        if (receita.getDataValidade() != null && receita.getDataValidade().isBefore(LocalDate.now())) {
            log.warn("Tentativa de usar receita vencida. ID: {}, Data Validade: {}", receita.getId(), receita.getDataValidade());
            throw new BadRequestException("Receita está vencida. Data de validade: " + receita.getDataValidade());
        }
    }
}

