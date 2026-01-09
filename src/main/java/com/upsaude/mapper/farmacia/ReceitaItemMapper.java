package com.upsaude.mapper.farmacia;

import com.upsaude.api.request.farmacia.ReceitaItemRequest;
import com.upsaude.api.response.farmacia.ReceitaItemResponse;
import com.upsaude.entity.farmacia.Receita;
import com.upsaude.entity.farmacia.ReceitaItem;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.List;

@Mapper(config = MappingConfig.class)
public interface ReceitaItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "receita", source = "receita")
    @Mapping(target = "sigtapProcedimento", ignore = true)
    @Mapping(target = "dispensacoes", ignore = true)
    @Mapping(target = "observacoes", source = "request.observacoes")
    ReceitaItem fromRequest(ReceitaItemRequest request, Receita receita);

    @Mapping(target = "receitaId", source = "receita.id")
    @Mapping(target = "sigtapProcedimentoId", source = "sigtapProcedimento.id")
    @Mapping(target = "procedimentoCodigo", ignore = true)
    @Mapping(target = "procedimentoNome", ignore = true)
    @Mapping(target = "quantidadeJaDispensada", ignore = true)
    ReceitaItemResponse toResponse(ReceitaItem receitaItem);

    default ReceitaItemResponse toResponseCompleto(ReceitaItem item) {
        if (item == null) return null;

        ReceitaItemResponse response = toResponse(item);

        if (item.getSigtapProcedimento() != null) {
            response.setProcedimentoCodigo(item.getSigtapProcedimento().getCodigoOficial());
            response.setProcedimentoNome(item.getSigtapProcedimento().getNome());
        }

        if (item.getDispensacoes() != null && !item.getDispensacoes().isEmpty()) {
            BigDecimal quantidadeJaDispensada = item.getDispensacoes().stream()
                    .map(di -> di.getQuantidadeDispensada() != null ? di.getQuantidadeDispensada() : BigDecimal.ZERO)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            response.setQuantidadeJaDispensada(quantidadeJaDispensada);
        } else {
            response.setQuantidadeJaDispensada(BigDecimal.ZERO);
        }

        return response;
    }

    default List<ReceitaItemResponse> toResponseList(List<ReceitaItem> itens) {
        if (itens == null) return null;
        return itens.stream()
                .map(this::toResponseCompleto)
                .toList();
    }
}
