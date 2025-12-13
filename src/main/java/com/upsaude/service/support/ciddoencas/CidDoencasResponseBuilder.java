package com.upsaude.service.support.ciddoencas;

import com.upsaude.api.response.CidDoencasResponse;
import com.upsaude.entity.CidDoencas;
import com.upsaude.mapper.CidDoencasMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CidDoencasResponseBuilder {

    private final CidDoencasMapper mapper;

    public CidDoencasResponse build(CidDoencas entity) {
        return mapper.toResponse(entity);
    }
}

