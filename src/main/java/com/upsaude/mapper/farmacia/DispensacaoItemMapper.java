package com.upsaude.mapper.farmacia;

import com.upsaude.api.request.farmacia.DispensacaoItemRequest;
import com.upsaude.api.response.farmacia.DispensacaoItemResponse;
import com.upsaude.entity.farmacia.Dispensacao;
import com.upsaude.entity.farmacia.DispensacaoItem;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface DispensacaoItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "dispensacao", source = "dispensacao")
    @Mapping(target = "receitaItem", ignore = true)
    @Mapping(target = "sigtapProcedimento", ignore = true)
    @Mapping(target = "observacoes", source = "request.observacoes")
    DispensacaoItem fromRequest(DispensacaoItemRequest request, Dispensacao dispensacao);

    @Mapping(target = "dispensacaoId", source = "dispensacao.id")
    @Mapping(target = "receitaItemId", source = "receitaItem.id")
    @Mapping(target = "sigtapProcedimentoId", source = "sigtapProcedimento.id")
    @Mapping(target = "procedimentoCodigo", ignore = true)
    @Mapping(target = "procedimentoNome", ignore = true)
    DispensacaoItemResponse toResponse(DispensacaoItem item);

    default DispensacaoItemResponse toResponseCompleto(DispensacaoItem item) {
        if (item == null) return null;

        DispensacaoItemResponse response = toResponse(item);

        if (item.getSigtapProcedimento() != null) {
            response.setProcedimentoCodigo(item.getSigtapProcedimento().getCodigoOficial());
            response.setProcedimentoNome(item.getSigtapProcedimento().getNome());
        }

        return response;
    }

    default java.util.List<DispensacaoItemResponse> toResponseList(java.util.List<DispensacaoItem> itens) {
        if (itens == null) return null;
        return itens.stream()
                .map(this::toResponseCompleto)
                .toList();
    }
}
