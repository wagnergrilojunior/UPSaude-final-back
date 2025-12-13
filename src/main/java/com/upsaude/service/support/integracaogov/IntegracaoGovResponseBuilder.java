package com.upsaude.service.support.integracaogov;

import com.upsaude.api.response.IntegracaoGovResponse;
import com.upsaude.entity.IntegracaoGov;
import com.upsaude.mapper.IntegracaoGovMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IntegracaoGovResponseBuilder {

    private final IntegracaoGovMapper mapper;

    public IntegracaoGovResponse build(IntegracaoGov entity) {
        return mapper.toResponse(entity);
    }
}
