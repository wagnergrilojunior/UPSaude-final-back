package com.upsaude.service.support.catalogoprocedimentos;

import com.upsaude.api.response.CatalogoProcedimentosResponse;
import com.upsaude.entity.CatalogoProcedimentos;
import com.upsaude.mapper.CatalogoProcedimentosMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CatalogoProcedimentosResponseBuilder {

    private final CatalogoProcedimentosMapper mapper;

    public CatalogoProcedimentosResponse build(CatalogoProcedimentos entity) {
        return mapper.toResponse(entity);
    }
}

