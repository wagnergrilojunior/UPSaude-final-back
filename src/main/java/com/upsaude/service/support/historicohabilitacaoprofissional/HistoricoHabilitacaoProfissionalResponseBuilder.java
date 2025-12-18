package com.upsaude.service.support.historicohabilitacaoprofissional;

import com.upsaude.api.response.profissional.HistoricoHabilitacaoProfissionalResponse;
import com.upsaude.entity.profissional.HistoricoHabilitacaoProfissional;
import com.upsaude.mapper.HistoricoHabilitacaoProfissionalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoricoHabilitacaoProfissionalResponseBuilder {

    private final HistoricoHabilitacaoProfissionalMapper mapper;

    public HistoricoHabilitacaoProfissionalResponse build(HistoricoHabilitacaoProfissional entity) {
        return mapper.toResponse(entity);
    }
}
