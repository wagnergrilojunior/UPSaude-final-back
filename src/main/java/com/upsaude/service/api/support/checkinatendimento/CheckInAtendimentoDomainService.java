package com.upsaude.service.api.support.checkinatendimento;

import com.upsaude.entity.clinica.atendimento.CheckInAtendimento;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CheckInAtendimentoDomainService {

    public void aplicarDefaults(CheckInAtendimento entity) {
        // sem regras adicionais por enquanto
    }

    public void validarPodeInativar(CheckInAtendimento entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar check-in já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Check-in já está inativo");
        }
    }

    public void validarPodeDeletar(CheckInAtendimento entity) {
        log.debug("Validando se check-in pode ser deletado. ID: {}", entity.getId());
        // Adicione aqui qualquer lógica de validação antes de um hard delete.
    }
}
