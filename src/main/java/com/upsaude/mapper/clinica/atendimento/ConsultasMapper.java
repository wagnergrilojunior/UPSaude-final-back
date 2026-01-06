package com.upsaude.mapper.clinica.atendimento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.clinica.atendimento.ConsultasRequest;
import com.upsaude.api.response.clinica.atendimento.ConsultasResponse;
import com.upsaude.entity.clinica.atendimento.Consultas;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.convenio.ConvenioMapper;
import com.upsaude.mapper.paciente.PacienteMapper;
import com.upsaude.mapper.profissional.MedicosMapper;
import com.upsaude.mapper.profissional.ProfissionaisSaudeMapper;

@Mapper(config = MappingConfig.class, uses = {ConvenioMapper.class, MedicosMapper.class, PacienteMapper.class, ProfissionaisSaudeMapper.class, com.upsaude.mapper.embeddable.InformacoesConsultaMapper.class, com.upsaude.mapper.embeddable.AnamneseConsultaMapper.class, com.upsaude.mapper.embeddable.DiagnosticoConsultaMapper.class, com.upsaude.mapper.embeddable.PrescricaoConsultaMapper.class, com.upsaude.mapper.embeddable.ExamesSolicitadosConsultaMapper.class, com.upsaude.mapper.embeddable.EncaminhamentoConsultaMapper.class, com.upsaude.mapper.embeddable.AtestadoConsultaMapper.class})
public interface ConsultasMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "convenio", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissionalSaude", ignore = true)
    Consultas fromRequest(ConsultasRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "convenio", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissionalSaude", ignore = true)
    void updateFromRequest(ConsultasRequest request, @MappingTarget Consultas entity);

    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "profissionalSaude", ignore = true)
    @Mapping(target = "convenio", ignore = true)
    ConsultasResponse toResponse(Consultas entity);
}
