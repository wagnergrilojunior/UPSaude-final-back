package com.upsaude.service.api.support.financeiro.reserva;

import com.upsaude.api.response.financeiro.ReservaOrcamentariaAssistencialResponse;
import com.upsaude.entity.financeiro.ReservaOrcamentariaAssistencial;
import com.upsaude.mapper.financeiro.ReservaOrcamentariaAssistencialMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservaOrcamentariaAssistencialResponseBuilder {

    private final ReservaOrcamentariaAssistencialMapper mapper;

    public ReservaOrcamentariaAssistencialResponse build(ReservaOrcamentariaAssistencial entity) {
        return mapper.toResponse(entity);
    }
}

