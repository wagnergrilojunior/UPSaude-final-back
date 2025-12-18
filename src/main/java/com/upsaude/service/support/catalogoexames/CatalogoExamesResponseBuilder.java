package com.upsaude.service.support.catalogoexames;

import org.springframework.stereotype.Service;

import com.upsaude.api.response.clinica.exame.CatalogoExamesResponse;
import com.upsaude.entity.clinica.exame.CatalogoExames;
import com.upsaude.mapper.clinica.exame.CatalogoExamesMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CatalogoExamesResponseBuilder {

    private final CatalogoExamesMapper mapper;

    public CatalogoExamesResponse build(CatalogoExames entity) {
        return mapper.toResponse(entity);
    }
}
