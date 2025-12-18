package com.upsaude.service.support.lgpdconsentimento;

import com.upsaude.entity.sistema.LGPDConsentimento;
import org.springframework.stereotype.Service;

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
}
