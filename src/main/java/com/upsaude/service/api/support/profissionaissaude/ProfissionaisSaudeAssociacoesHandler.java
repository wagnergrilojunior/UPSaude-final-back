package com.upsaude.service.api.support.profissionaissaude;

import com.upsaude.api.request.profissional.ProfissionaisSaudeRequest;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfissionaisSaudeAssociacoesHandler {

    public ProfissionaisSaude processarEspecialidades(ProfissionaisSaude profissional, ProfissionaisSaudeRequest request) {
        log.debug("Especialidades removidas do sistema. Nenhuma processamento necessário.");
        return profissional;
    }
}
