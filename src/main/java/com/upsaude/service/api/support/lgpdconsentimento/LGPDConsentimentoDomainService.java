package com.upsaude.service.api.support.lgpdconsentimento;

import com.upsaude.entity.sistema.lgpd.LGPDConsentimento;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LGPDConsentimentoDomainService {

    public void aplicarDefaults(LGPDConsentimento entity) {
        if (entity.getAutorizacaoUsoDados() == null) {
            entity.setAutorizacaoUsoDados(false);
        }
        if (entity.getAutorizacaoContatoWhatsApp() == null) {
            entity.setAutorizacaoContatoWhatsApp(false);
        }
        if (entity.getAutorizacaoContatoEmail() == null) {
            entity.setAutorizacaoContatoEmail(false);
        }
    }

    public void validarPodeInativar(LGPDConsentimento entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar consentimento LGPD já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Consentimento LGPD já está inativo");
        }
    }

    public void validarPodeDeletar(LGPDConsentimento entity) {
        log.debug("Validando se consentimento LGPD pode ser deletado. ID: {}", entity.getId());
        // Adicione aqui qualquer lógica de validação antes de um hard delete.
    }
}
