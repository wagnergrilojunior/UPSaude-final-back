package com.upsaude.mapper.financeiro;

import com.upsaude.api.request.financeiro.BaixaReceberRequest;
import com.upsaude.api.response.financeiro.BaixaReceberResponse;
import com.upsaude.api.response.financeiro.BaixaReceberSimplificadaResponse;
import com.upsaude.api.response.financeiro.TituloReceberSimplificadoResponse;
import com.upsaude.entity.financeiro.BaixaReceber;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MappingConfig.class, uses = { ContaFinanceiraMapper.class, MovimentacaoContaMapper.class })
public interface BaixaReceberMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "tituloReceber", ignore = true)
    @Mapping(target = "contaFinanceira", ignore = true)
    @Mapping(target = "movimentacaoConta", ignore = true)
    @Mapping(target = "lancamentoFinanceiro", ignore = true)
    @Mapping(target = "idempotencyKey", ignore = true)
    BaixaReceber fromRequest(BaixaReceberRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "tituloReceber", ignore = true)
    @Mapping(target = "contaFinanceira", ignore = true)
    @Mapping(target = "movimentacaoConta", ignore = true)
    @Mapping(target = "lancamentoFinanceiro", ignore = true)
    @Mapping(target = "idempotencyKey", ignore = true)
    void updateFromRequest(BaixaReceberRequest request, @MappingTarget BaixaReceber entity);

    BaixaReceberResponse toResponse(BaixaReceber entity);

    @Named("toSimplifiedResponse")
    BaixaReceberSimplificadaResponse toSimplifiedResponse(BaixaReceber entity);

    default TituloReceberSimplificadoResponse mapTituloReceber(com.upsaude.entity.financeiro.TituloReceber titulo) {
        if (titulo == null) {
            return null;
        }
        try {
            return TituloReceberSimplificadoResponse.builder()
                    .id(titulo.getId())
                    .numero(titulo.getNumero())
                    .valorAberto(titulo.getValorAberto())
                    .dataVencimento(titulo.getDataVencimento())
                    .status(titulo.getStatus())
                    .build();
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return null;
        }
    }
}

