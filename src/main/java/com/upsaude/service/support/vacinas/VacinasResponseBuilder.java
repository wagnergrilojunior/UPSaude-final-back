package com.upsaude.service.support.vacinas;

import org.springframework.stereotype.Service;

import com.upsaude.api.response.saude_publica.vacina.VacinasResponse;
import com.upsaude.entity.saude_publica.vacina.Vacinas;
import com.upsaude.mapper.saude_publica.vacina.VacinasMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VacinasResponseBuilder {

    private final VacinasMapper mapper;

    public VacinasResponse build(Vacinas entity) {
        return mapper.toResponse(entity);
    }
}
