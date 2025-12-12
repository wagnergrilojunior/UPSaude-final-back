package com.upsaude.service.support.alergias;

import com.upsaude.api.response.AlergiasResponse;
import com.upsaude.entity.Alergias;
import com.upsaude.mapper.AlergiasMapper;
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
