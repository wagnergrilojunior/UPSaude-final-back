package com.upsaude.mapper.financeiro;

import com.upsaude.api.request.financeiro.RenegociacaoReceberRequest;
import com.upsaude.api.response.financeiro.RenegociacaoReceberResponse;
import com.upsaude.entity.financeiro.RenegociacaoReceber;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface RenegociacaoReceberMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    RenegociacaoReceber fromRequest(RenegociacaoReceberRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateFromRequest(RenegociacaoReceberRequest request, @MappingTarget RenegociacaoReceber entity);

    RenegociacaoReceberResponse toResponse(RenegociacaoReceber entity);
}

