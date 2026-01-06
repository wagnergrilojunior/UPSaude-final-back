package com.upsaude.mapper.clinica.prontuario;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.clinica.prontuario.VacinacaoPacienteRequest;
import com.upsaude.api.response.clinica.prontuario.VacinacaoPacienteResponse;
import com.upsaude.entity.clinica.prontuario.VacinacaoPaciente;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.profissional.ProfissionaisSaudeMapper;
import com.upsaude.mapper.referencia.cid.Cid10SubcategoriaMapper;
import com.upsaude.mapper.sigtap.SigtapProcedimentoMapper;

@Mapper(config = MappingConfig.class, uses = {ProfissionaisSaudeMapper.class, Cid10SubcategoriaMapper.class, SigtapProcedimentoMapper.class})
public interface VacinacaoPacienteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "prontuario", ignore = true)
    @Mapping(target = "profissionalAplicador", ignore = true)
    @Mapping(target = "procedimento", ignore = true)
    @Mapping(target = "diagnosticoRelacionado", ignore = true)
    VacinacaoPaciente fromRequest(VacinacaoPacienteRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "prontuario", ignore = true)
    @Mapping(target = "profissionalAplicador", ignore = true)
    @Mapping(target = "procedimento", ignore = true)
    @Mapping(target = "diagnosticoRelacionado", ignore = true)
    void updateFromRequest(VacinacaoPacienteRequest request, @MappingTarget VacinacaoPaciente entity);

    VacinacaoPacienteResponse toResponse(VacinacaoPaciente entity);
}

