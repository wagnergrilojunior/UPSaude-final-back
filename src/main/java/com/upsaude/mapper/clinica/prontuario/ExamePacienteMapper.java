package com.upsaude.mapper.clinica.prontuario;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.clinica.prontuario.ExamePacienteRequest;
import com.upsaude.api.response.clinica.prontuario.ExamePacienteResponse;
import com.upsaude.entity.clinica.prontuario.ExamePaciente;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.profissional.ProfissionaisSaudeMapper;
import com.upsaude.mapper.referencia.cid.Cid10SubcategoriaMapper;
import com.upsaude.mapper.sigtap.SigtapProcedimentoMapper;

@Mapper(config = MappingConfig.class, uses = {ProfissionaisSaudeMapper.class, Cid10SubcategoriaMapper.class, SigtapProcedimentoMapper.class})
public interface ExamePacienteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "prontuario", ignore = true)
    @Mapping(target = "profissionalSolicitante", ignore = true)
    @Mapping(target = "profissionalResponsavel", ignore = true)
    @Mapping(target = "procedimento", ignore = true)
    @Mapping(target = "diagnosticoRelacionado", ignore = true)
    ExamePaciente fromRequest(ExamePacienteRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "prontuario", ignore = true)
    @Mapping(target = "profissionalSolicitante", ignore = true)
    @Mapping(target = "profissionalResponsavel", ignore = true)
    @Mapping(target = "procedimento", ignore = true)
    @Mapping(target = "diagnosticoRelacionado", ignore = true)
    void updateFromRequest(ExamePacienteRequest request, @MappingTarget ExamePaciente entity);

    ExamePacienteResponse toResponse(ExamePaciente entity);
}

