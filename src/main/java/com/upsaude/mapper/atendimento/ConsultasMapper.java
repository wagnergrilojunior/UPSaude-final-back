package com.upsaude.mapper.atendimento;
import com.upsaude.api.response.paciente.ResponsavelLegalResponse;
import com.upsaude.api.response.paciente.PacienteResponse;

import com.upsaude.api.request.atendimento.ConsultasRequest;
import com.upsaude.api.response.atendimento.ConsultasResponse;
import com.upsaude.dto.ConsultasDTO;
import com.upsaude.entity.atendimento.Consultas;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {ConvenioMapper.class, EspecialidadesMedicasMapper.class, MedicosMapper.class, PacienteMapper.class, ProfissionaisSaudeMapper.class, com.upsaude.mapper.embeddable.InformacoesConsultaMapper.class, com.upsaude.mapper.embeddable.AnamneseConsultaMapper.class, com.upsaude.mapper.embeddable.DiagnosticoConsultaMapper.class, com.upsaude.mapper.embeddable.PrescricaoConsultaMapper.class, com.upsaude.mapper.embeddable.ExamesSolicitadosConsultaMapper.class, com.upsaude.mapper.embeddable.EncaminhamentoConsultaMapper.class, com.upsaude.mapper.embeddable.AtestadoConsultaMapper.class})
public interface ConsultasMapper extends EntityMapper<Consultas, ConsultasDTO> {

    @Mapping(target = "active", ignore = true)
    Consultas toEntity(ConsultasDTO dto);

    ConsultasDTO toDTO(Consultas entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "convenio", ignore = true)
    @Mapping(target = "especialidade", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissionalSaude", ignore = true)
    Consultas fromRequest(ConsultasRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "convenio", ignore = true)
    @Mapping(target = "especialidade", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissionalSaude", ignore = true)
    void updateFromRequest(ConsultasRequest request, @MappingTarget Consultas entity);

    // Evita ciclos/recursões indiretas via PacienteResponse/ResponsavelLegalResponse e grafos grandes.
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "profissionalSaude", ignore = true)
    @Mapping(target = "especialidade", ignore = true)
    @Mapping(target = "convenio", ignore = true)
    ConsultasResponse toResponse(Consultas entity);
}
