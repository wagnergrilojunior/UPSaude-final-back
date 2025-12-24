package com.upsaude.service.api.support.alergias;

import com.upsaude.api.response.alergia.AlergiasResponse;
import com.upsaude.entity.paciente.alergia.Alergias;
import com.upsaude.mapper.paciente.alergia.AlergiasMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlergiasResponseBuilder {

    private final AlergiasMapper mapper;

    public AlergiasResponse build(Alergias entity) {
        return mapper.toResponse(entity);
    }
}
