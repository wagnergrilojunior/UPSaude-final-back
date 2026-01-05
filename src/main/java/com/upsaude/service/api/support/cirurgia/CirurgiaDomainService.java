package com.upsaude.service.api.support.cirurgia;

import com.upsaude.entity.clinica.cirurgia.Cirurgia;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CirurgiaDomainService {

    public void validarPodeInativar(Cirurgia entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar cirurgia já inativa. ID: {}", entity.getId());
            throw new BadRequestException("Cirurgia já está inativa");
        }
    }

    public void validarPodeDeletar(Cirurgia entity) {
        // Validações adicionais podem ser adicionadas aqui se necessário
        // Por exemplo, verificar se há registros relacionados que impedem a exclusão
    }
}

