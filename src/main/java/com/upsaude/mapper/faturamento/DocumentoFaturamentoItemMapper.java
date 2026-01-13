package com.upsaude.mapper.faturamento;

import com.upsaude.api.request.faturamento.DocumentoFaturamentoItemRequest;
import com.upsaude.api.response.faturamento.DocumentoFaturamentoItemResponse;
import com.upsaude.api.response.faturamento.DocumentoFaturamentoItemSimplificadoResponse;
import com.upsaude.api.response.faturamento.DocumentoFaturamentoSimplificadoResponse;
import com.upsaude.api.response.referencia.sigtap.ProcedimentoSigtapSimplificadoResponse;
import com.upsaude.entity.faturamento.DocumentoFaturamento;
import com.upsaude.entity.faturamento.DocumentoFaturamentoItem;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimento;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MappingConfig.class)
public interface DocumentoFaturamentoItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "documento", ignore = true)
    @Mapping(target = "sigtapProcedimento", ignore = true)
    DocumentoFaturamentoItem fromRequest(DocumentoFaturamentoItemRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "documento", ignore = true)
    @Mapping(target = "sigtapProcedimento", ignore = true)
    void updateFromRequest(DocumentoFaturamentoItemRequest request, @MappingTarget DocumentoFaturamentoItem entity);

    @Mapping(target = "documento", source = "documento", qualifiedByName = "mapDocumentoSimplificado")
    @Mapping(target = "sigtapProcedimento", source = "sigtapProcedimento", qualifiedByName = "mapSigtapProcedimentoSimplificado")
    DocumentoFaturamentoItemResponse toResponse(DocumentoFaturamentoItem entity);

    @Named("toSimplifiedResponse")
    DocumentoFaturamentoItemSimplificadoResponse toSimplifiedResponse(DocumentoFaturamentoItem entity);

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

    @Named("mapSigtapProcedimentoSimplificado")
    default ProcedimentoSigtapSimplificadoResponse mapSigtapProcedimentoSimplificado(SigtapProcedimento procedimento) {
        if (procedimento == null) return null;
        try {
            return ProcedimentoSigtapSimplificadoResponse.builder()
                    .id(procedimento.getId())
                    .codigoOficial(procedimento.getCodigoOficial())
                    .nome(procedimento.getNome())
                    .valorServicoHospitalar(procedimento.getValorServicoHospitalar())
                    .valorServicoAmbulatorial(procedimento.getValorServicoAmbulatorial())
                    .valorServicoProfissional(procedimento.getValorServicoProfissional())
                    .build();
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return null;
        }
    }
}

