package com.upsaude.mapper.clinica.atendimento;

import com.upsaude.api.request.clinica.atendimento.AtendimentoProcedimentoRequest;
import com.upsaude.api.response.clinica.atendimento.AtendimentoProcedimentoResponse;
import com.upsaude.api.response.clinica.atendimento.AtendimentoProcedimentoSimplificadoResponse;
import com.upsaude.api.response.clinica.atendimento.AtendimentoSimplificadoResponse;
import com.upsaude.api.response.referencia.sigtap.ProcedimentoSigtapSimplificadoResponse;
import com.upsaude.entity.clinica.atendimento.Atendimento;
import com.upsaude.entity.clinica.atendimento.AtendimentoProcedimento;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimento;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MappingConfig.class)
public interface AtendimentoProcedimentoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "sigtapProcedimento", ignore = true)
    AtendimentoProcedimento fromRequest(AtendimentoProcedimentoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "sigtapProcedimento", ignore = true)
    void updateFromRequest(AtendimentoProcedimentoRequest request, @MappingTarget AtendimentoProcedimento entity);

    @Mapping(target = "atendimento", source = "atendimento", qualifiedByName = "mapAtendimentoSimplificado")
    @Mapping(target = "sigtapProcedimento", source = "sigtapProcedimento", qualifiedByName = "mapSigtapSimplificado")
    AtendimentoProcedimentoResponse toResponse(AtendimentoProcedimento entity);

    @Named("toSimplifiedResponse")
    @Mapping(target = "sigtapProcedimento", source = "sigtapProcedimento", qualifiedByName = "mapSigtapSimplificado")
    AtendimentoProcedimentoSimplificadoResponse toSimplifiedResponse(AtendimentoProcedimento entity);

    @Named("mapAtendimentoSimplificado")
    default AtendimentoSimplificadoResponse mapAtendimentoSimplificado(Atendimento atendimento) {
        if (atendimento == null) return null;
        try {
            return AtendimentoSimplificadoResponse.builder()
                    .id(atendimento.getId())
                    .dataHora(atendimento.getInformacoes() != null ? atendimento.getInformacoes().getDataHora() : null)
                    .statusAtendimento(atendimento.getInformacoes() != null ? atendimento.getInformacoes().getStatusAtendimento() : null)
                    .build();
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return null;
        }
    }

    @Named("mapSigtapSimplificado")
    default ProcedimentoSigtapSimplificadoResponse mapSigtapSimplificado(SigtapProcedimento procedimento) {
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

