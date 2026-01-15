package com.upsaude.mapper.financeiro;

import com.upsaude.api.request.financeiro.LancamentoFinanceiroRequest;
import com.upsaude.api.response.faturamento.DocumentoFaturamentoSimplificadoResponse;
import com.upsaude.api.response.financeiro.LancamentoFinanceiroResponse;
import com.upsaude.api.response.financeiro.LancamentoFinanceiroSimplificadoResponse;
import com.upsaude.api.response.sistema.usuario.UsuarioSistemaSimplificadoResponse;
import com.upsaude.entity.financeiro.LancamentoFinanceiro;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MappingConfig.class, uses = { CompetenciaFinanceiraMapper.class, TituloReceberMapper.class, TituloPagarMapper.class, LancamentoFinanceiroItemMapper.class, UsuarioSistemaMapper.class })
public interface LancamentoFinanceiroMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "competencia", ignore = true)
    @Mapping(target = "documentoFaturamento", ignore = true)
    @Mapping(target = "tituloReceber", ignore = true)
    @Mapping(target = "tituloPagar", ignore = true)
    @Mapping(target = "conciliacao", ignore = true)
    @Mapping(target = "idempotencyKey", ignore = true)
    @Mapping(target = "travado", ignore = true)
    @Mapping(target = "travadoEm", ignore = true)
    @Mapping(target = "itens", ignore = true)
    LancamentoFinanceiro fromRequest(LancamentoFinanceiroRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "competencia", ignore = true)
    @Mapping(target = "documentoFaturamento", ignore = true)
    @Mapping(target = "tituloReceber", ignore = true)
    @Mapping(target = "tituloPagar", ignore = true)
    @Mapping(target = "conciliacao", ignore = true)
    @Mapping(target = "idempotencyKey", ignore = true)
    @Mapping(target = "travado", ignore = true)
    @Mapping(target = "travadoEm", ignore = true)
    @Mapping(target = "itens", ignore = true)
    void updateFromRequest(LancamentoFinanceiroRequest request, @MappingTarget LancamentoFinanceiro entity);

    @Mapping(target = "documentoFaturamento", source = "documentoFaturamento", qualifiedByName = "mapDocumentoFaturamentoSimplificado")
    @Mapping(target = "tituloReceber", source = "tituloReceber", qualifiedByName = "toSimplifiedResponse")
    @Mapping(target = "tituloPagar", source = "tituloPagar", qualifiedByName = "toSimplifiedResponse")
    @Mapping(target = "travadoPor", source = "travadoPor", qualifiedByName = "mapUsuarioSistemaSimplificado")
    LancamentoFinanceiroResponse toResponse(LancamentoFinanceiro entity);

    @Named("toSimplifiedResponse")
    LancamentoFinanceiroSimplificadoResponse toSimplifiedResponse(LancamentoFinanceiro entity);

    @Named("mapDocumentoFaturamentoSimplificado")
    default DocumentoFaturamentoSimplificadoResponse mapDocumentoFaturamentoSimplificado(com.upsaude.entity.faturamento.DocumentoFaturamento documento) {
        if (documento == null) return null;
        try {
            return DocumentoFaturamentoSimplificadoResponse.builder()
                    .id(documento.getId())
                    .tipo(documento.getTipo())
                    .numero(documento.getNumero())
                    .serie(documento.getSerie())
                    .status(documento.getStatus())
                    .build();
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return null;
        }
    }

}

