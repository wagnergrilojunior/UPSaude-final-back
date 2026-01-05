package com.upsaude.service.api.support.integracaogov;

import org.springframework.stereotype.Service;

import com.upsaude.entity.sistema.integracao.IntegracaoGov;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IntegracaoGovDomainService {

    public void aplicarDefaults(IntegracaoGov entity) {
        // sem regras adicionais por enquanto
    }

    public void validarPodeInativar(IntegracaoGov entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar integração gov já inativa. ID: {}", entity.getId());
            throw new BadRequestException("Integração gov já está inativa");
        }
    }

    public void validarPodeDeletar(IntegracaoGov entity) {
        log.debug("Validando se integração gov pode ser deletada. ID: {}", entity.getId());
        // Adicione aqui qualquer lógica de validação antes de um hard delete.
    }
}
