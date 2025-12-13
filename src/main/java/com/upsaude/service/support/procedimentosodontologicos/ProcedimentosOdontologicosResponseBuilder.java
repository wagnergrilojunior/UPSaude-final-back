package com.upsaude.service.support.procedimentosodontologicos;

import com.upsaude.api.response.ProcedimentosOdontologicosResponse;
import com.upsaude.entity.ProcedimentosOdontologicos;
import com.upsaude.mapper.ProcedimentosOdontologicosMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcedimentosOdontologicosResponseBuilder {

    private final ProcedimentosOdontologicosMapper mapper;

    public ProcedimentosOdontologicosResponse build(ProcedimentosOdontologicos entity) {
        return mapper.toResponse(entity);
    }
}

