package com.upsaude.service.support.doencas;

import com.upsaude.api.response.DoencasResponse;
import com.upsaude.entity.Doencas;
import com.upsaude.mapper.DoencasMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoencasResponseBuilder {

    private final DoencasMapper mapper;

    public DoencasResponse build(Doencas entity) {
        return mapper.toResponse(entity);
    }
}

