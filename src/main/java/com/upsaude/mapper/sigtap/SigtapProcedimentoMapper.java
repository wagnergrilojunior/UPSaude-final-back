package com.upsaude.mapper.sigtap;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.upsaude.api.response.referencia.sigtap.SigtapProcedimentoResponse;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimento;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", uses = {SigtapFinanciamentoMapper.class, SigtapRubricaMapper.class})
public interface SigtapProcedimentoMapper {

    @Mapping(target = "grupoCodigo", source = "formaOrganizacao.subgrupo.grupo.codigoOficial")
    @Mapping(target = "grupoNome", source = "formaOrganizacao.subgrupo.grupo.nome")
    @Mapping(target = "subgrupoCodigo", source = "formaOrganizacao.subgrupo.codigoOficial")
    @Mapping(target = "subgrupoNome", source = "formaOrganizacao.subgrupo.nome")
    @Mapping(target = "formaOrganizacaoCodigo", source = "formaOrganizacao.codigoOficial")
    @Mapping(target = "formaOrganizacaoNome", source = "formaOrganizacao.nome")
    @Mapping(target = "financiamento", source = "financiamento")
    @Mapping(target = "rubrica", source = "rubrica")
    @Mapping(target = "totalAmbulatorial", expression = "java(calcularTotalAmbulatorial(entity))")
    @Mapping(target = "totalHospitalar", expression = "java(calcularTotalHospitalar(entity))")
    @Mapping(target = "atributosComplementares", ignore = true)
    SigtapProcedimentoResponse toResponse(SigtapProcedimento entity);

    default BigDecimal calcularTotalAmbulatorial(SigtapProcedimento entity) {
        if (entity == null) {
            return null;
        }
        BigDecimal sa = entity.getValorServicoAmbulatorial() != null ? entity.getValorServicoAmbulatorial() : BigDecimal.ZERO;
        BigDecimal sp = entity.getValorServicoProfissional() != null ? entity.getValorServicoProfissional() : BigDecimal.ZERO;
        return sa.add(sp);
    }

    default BigDecimal calcularTotalHospitalar(SigtapProcedimento entity) {
        if (entity == null) {
            return null;
        }
        BigDecimal sh = entity.getValorServicoHospitalar() != null ? entity.getValorServicoHospitalar() : BigDecimal.ZERO;
        BigDecimal sp = entity.getValorServicoProfissional() != null ? entity.getValorServicoProfissional() : BigDecimal.ZERO;
        return sh.add(sp);
    }
}

