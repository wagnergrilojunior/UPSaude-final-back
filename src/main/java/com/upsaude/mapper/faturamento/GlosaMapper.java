package com.upsaude.mapper.faturamento;

import com.upsaude.api.request.faturamento.GlosaRequest;
import com.upsaude.api.response.faturamento.DocumentoFaturamentoItemSimplificadoResponse;
import com.upsaude.api.response.faturamento.DocumentoFaturamentoSimplificadoResponse;
import com.upsaude.api.response.faturamento.GlosaResponse;
import com.upsaude.entity.faturamento.DocumentoFaturamento;
import com.upsaude.entity.faturamento.DocumentoFaturamentoItem;
import com.upsaude.entity.faturamento.Glosa;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MappingConfig.class)
public interface GlosaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "documento", ignore = true)
    @Mapping(target = "item", ignore = true)
    Glosa fromRequest(GlosaRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "documento", ignore = true)
    @Mapping(target = "item", ignore = true)
    void updateFromRequest(GlosaRequest request, @MappingTarget Glosa entity);

    @Mapping(target = "documento", source = "documento", qualifiedByName = "mapDocumentoSimplificado")
    @Mapping(target = "item", source = "item", qualifiedByName = "mapItemSimplificado")
    GlosaResponse toResponse(Glosa entity);

    @Named("mapDocumentoSimplificado")
    default DocumentoFaturamentoSimplificadoResponse mapDocumentoSimplificado(DocumentoFaturamento documento) {
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

    @Named("mapItemSimplificado")
    default DocumentoFaturamentoItemSimplificadoResponse mapItemSimplificado(DocumentoFaturamentoItem item) {
        if (item == null) return null;
        try {
            return DocumentoFaturamentoItemSimplificadoResponse.builder()
                    .id(item.getId())
                    .quantidade(item.getQuantidade())
                    .valorTotal(item.getValorTotal())
                    .build();
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return null;
        }
    }
}

