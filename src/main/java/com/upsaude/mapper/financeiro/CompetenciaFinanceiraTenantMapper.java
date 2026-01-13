package com.upsaude.mapper.financeiro;

import com.upsaude.api.request.financeiro.CompetenciaFinanceiraTenantRequest;
import com.upsaude.api.response.financeiro.CompetenciaFinanceiraTenantResponse;
import com.upsaude.api.response.faturamento.DocumentoFaturamentoSimplificadoResponse;
import com.upsaude.entity.financeiro.CompetenciaFinanceiraTenant;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = { CompetenciaFinanceiraMapper.class })
public interface CompetenciaFinanceiraTenantMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "competencia", ignore = true)
    @Mapping(target = "documentoBpaFechamento", ignore = true)
    CompetenciaFinanceiraTenant fromRequest(CompetenciaFinanceiraTenantRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "competencia", ignore = true)
    @Mapping(target = "documentoBpaFechamento", ignore = true)
    void updateFromRequest(CompetenciaFinanceiraTenantRequest request, @MappingTarget CompetenciaFinanceiraTenant entity);

    CompetenciaFinanceiraTenantResponse toResponse(CompetenciaFinanceiraTenant entity);

    default DocumentoFaturamentoSimplificadoResponse mapDocumentoBpaFechamento(
            com.upsaude.entity.faturamento.DocumentoFaturamento documento) {
        if (documento == null) {
            return null;
        }
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

