package com.upsaude.service.support.vacinas;

import com.upsaude.api.response.VacinasResponse;
import com.upsaude.entity.Vacinas;
import com.upsaude.mapper.VacinasMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VacinasResponseBuilder {

    private final VacinasMapper mapper;

    public VacinasResponse build(Vacinas entity) {
        return mapper.toResponse(entity);
    }
}
