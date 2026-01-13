package com.upsaude.mapper.financeiro;

import com.upsaude.api.request.financeiro.ConciliacaoItemRequest;
import com.upsaude.api.response.financeiro.ConciliacaoItemResponse;
import com.upsaude.entity.financeiro.ConciliacaoItem;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {
        ConciliacaoBancariaMapper.class,
        ExtratoBancarioImportadoMapper.class,
        MovimentacaoContaMapper.class
})
public interface ConciliacaoItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "conciliacao", ignore = true)
    @Mapping(target = "extratoImportado", ignore = true)
    @Mapping(target = "movimentacaoConta", ignore = true)
    ConciliacaoItem fromRequest(ConciliacaoItemRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "conciliacao", ignore = true)
    @Mapping(target = "extratoImportado", ignore = true)
    @Mapping(target = "movimentacaoConta", ignore = true)
    void updateFromRequest(ConciliacaoItemRequest request, @MappingTarget ConciliacaoItem entity);

    ConciliacaoItemResponse toResponse(ConciliacaoItem entity);
}

