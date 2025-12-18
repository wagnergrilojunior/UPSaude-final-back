package com.upsaude.service.support.historicoclinico;

import com.upsaude.api.response.prontuario.HistoricoClinicoResponse;
import com.upsaude.entity.prontuario.HistoricoClinico;
import com.upsaude.mapper.HistoricoClinicoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoricoClinicoResponseBuilder {

    private final HistoricoClinicoMapper mapper;

    public HistoricoClinicoResponse build(HistoricoClinico entity) {
        return mapper.toResponse(entity);
    }
}
