package com.upsaude.service.support.checkinatendimento;

import com.upsaude.entity.clinica.atendimento.CheckInAtendimento;
import org.springframework.stereotype.Service;

@Service
public class CheckInAtendimentoDomainService {

    public void aplicarDefaults(CheckInAtendimento entity) {
        // sem regras adicionais por enquanto
    }
}
