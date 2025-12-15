package com.upsaude.service.support.historicoclinico;

import com.upsaude.api.response.HistoricoClinicoResponse;
import com.upsaude.entity.HistoricoClinico;
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
