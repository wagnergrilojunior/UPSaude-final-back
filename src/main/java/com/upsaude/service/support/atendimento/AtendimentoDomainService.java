package com.upsaude.service.support.atendimento;

import com.upsaude.entity.clinica.atendimento.Atendimento;
import org.springframework.stereotype.Service;

@Service
public class AtendimentoDomainService {

    public void aplicarDefaults(Atendimento entity) {
        // sem regras adicionais por enquanto
    }
}
