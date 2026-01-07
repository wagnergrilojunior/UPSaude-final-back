package com.upsaude.mapper.clinica.cirurgia;

import com.upsaude.api.request.clinica.cirurgia.CirurgiaProcedimentoRequest;
import com.upsaude.api.response.clinica.cirurgia.CirurgiaProcedimentoResponse;
import com.upsaude.api.response.referencia.sigtap.ProcedimentoSigtapSimplificadoResponse;
import com.upsaude.entity.clinica.cirurgia.CirurgiaProcedimento;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimento;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MappingConfig.class)
public interface CirurgiaProcedimentoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "cirurgia", ignore = true)
    @Mapping(target = "procedimento", ignore = true)
    CirurgiaProcedimento fromRequest(CirurgiaProcedimentoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "cirurgia", ignore = true)
    @Mapping(target = "procedimento", ignore = true)
    void updateFromRequest(CirurgiaProcedimentoRequest request, @MappingTarget CirurgiaProcedimento entity);

    @Mapping(target = "tenantId", source = "tenant.id")
    @Mapping(target = "estabelecimentoId", source = "estabelecimento.id")
    @Mapping(target = "procedimento", source = "procedimento", qualifiedByName = "mapProcedimentoSimplificado")
    CirurgiaProcedimentoResponse toResponse(CirurgiaProcedimento entity);

    @Named("mapProcedimentoSimplificado")
    default ProcedimentoSigtapSimplificadoResponse mapProcedimentoSimplificado(SigtapProcedimento procedimento) {
        if (procedimento == null) {
            return null;
        }
        return ProcedimentoSigtapSimplificadoResponse.builder()
                .id(procedimento.getId())
                .codigoOficial(procedimento.getCodigoOficial())
                .nome(procedimento.getNome())
                .valorServicoHospitalar(procedimento.getValorServicoHospitalar())
                .valorServicoAmbulatorial(procedimento.getValorServicoAmbulatorial())
                .valorServicoProfissional(procedimento.getValorServicoProfissional())
                .build();
    }
}

