package com.upsaude.service.support.catalogoexames;

import com.upsaude.api.response.CatalogoExamesResponse;
import com.upsaude.entity.CatalogoExames;
import com.upsaude.mapper.CatalogoExamesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CatalogoExamesResponseBuilder {

    private final CatalogoExamesMapper mapper;

    public CatalogoExamesResponse build(CatalogoExames entity) {
        return mapper.toResponse(entity);
    }
}
