package com.upsaude.mapper.financeiro;

import com.upsaude.api.request.financeiro.CompetenciaFinanceiraRequest;
import com.upsaude.api.response.financeiro.CompetenciaFinanceiraResponse;
import com.upsaude.api.response.faturamento.DocumentoFaturamentoSimplificadoResponse;
import com.upsaude.api.response.sistema.usuario.UsuarioSistemaSimplificadoResponse;
import com.upsaude.entity.financeiro.CompetenciaFinanceira;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MappingConfig.class, uses = { UsuarioSistemaMapper.class })
public interface CompetenciaFinanceiraMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "fechadaEm", ignore = true)
    @Mapping(target = "fechadaPor", ignore = true)
    @Mapping(target = "motivoFechamento", ignore = true)
    @Mapping(target = "snapshotHash", ignore = true)
    @Mapping(target = "documentoBpaFechamento", ignore = true)
    @Mapping(target = "hashMovimentacoes", ignore = true)
    @Mapping(target = "hashBpa", ignore = true)
    @Mapping(target = "validacaoIntegridade", ignore = true)
    CompetenciaFinanceira fromRequest(CompetenciaFinanceiraRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "fechadaEm", ignore = true)
    @Mapping(target = "fechadaPor", ignore = true)
    @Mapping(target = "motivoFechamento", ignore = true)
    @Mapping(target = "snapshotHash", ignore = true)
    @Mapping(target = "documentoBpaFechamento", ignore = true)
    @Mapping(target = "hashMovimentacoes", ignore = true)
    @Mapping(target = "hashBpa", ignore = true)
    @Mapping(target = "validacaoIntegridade", ignore = true)
    void updateFromRequest(CompetenciaFinanceiraRequest request, @MappingTarget CompetenciaFinanceira entity);

    @Mapping(target = "fechadaPor", source = "fechadaPor", qualifiedByName = "mapUsuarioSistemaSimplificado")
    @Mapping(target = "documentoBpaFechamento", expression = "java(mapDocumentoBpaFechamentoParaCompetencia(entity.getDocumentoBpaFechamento()))")
    CompetenciaFinanceiraResponse toResponse(CompetenciaFinanceira entity);

    @Named("mapDocumentoBpaFechamentoParaCompetencia")
    default DocumentoFaturamentoSimplificadoResponse mapDocumentoBpaFechamentoParaCompetencia(
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

