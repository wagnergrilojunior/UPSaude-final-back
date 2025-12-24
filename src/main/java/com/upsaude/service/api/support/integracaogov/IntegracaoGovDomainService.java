package com.upsaude.service.api.support.integracaogov;

import org.springframework.stereotype.Service;

import com.upsaude.entity.sistema.integracao.IntegracaoGov;

@Service
public class IntegracaoGovDomainService {

    public void aplicarDefaults(IntegracaoGov entity) {
        // sem regras adicionais por enquanto
    }
}
