package com.upsaude.mapper.atendimento;

import com.upsaude.api.request.atendimento.AtendimentoRequest;
import com.upsaude.api.response.atendimento.AtendimentoResponse;
import com.upsaude.dto.AtendimentoDTO;
import com.upsaude.entity.atendimento.Atendimento;
import com.upsaude.entity.convenio.Convenio;
import com.upsaude.entity.equipe.EquipeSaude;
import com.upsaude.entity.profissional.EspecialidadesMedicas;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {ConvenioMapper.class, EquipeSaudeMapper.class, EspecialidadesMedicasMapper.class, PacienteMapper.class, ProfissionaisSaudeMapper.class, com.upsaude.mapper.embeddable.InformacoesAtendimentoMapper.class, com.upsaude.mapper.embeddable.AnamneseAtendimentoMapper.class, com.upsaude.mapper.embeddable.DiagnosticoAtendimentoMapper.class, com.upsaude.mapper.embeddable.ProcedimentosRealizadosAtendimentoMapper.class, com.upsaude.mapper.embeddable.ClassificacaoRiscoAtendimentoMapper.class})
public interface AtendimentoMapper extends EntityMapper<Atendimento, AtendimentoDTO> {

    @Mapping(target = "active", ignore = true)
    Atendimento toEntity(AtendimentoDTO dto);

    AtendimentoDTO toDTO(Atendimento entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "convenio", ignore = true)
    @Mapping(target = "equipeSaude", ignore = true)
    @Mapping(target = "especialidade", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    Atendimento fromRequest(AtendimentoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "convenio", ignore = true)
    @Mapping(target = "equipeSaude", ignore = true)
    @Mapping(target = "especialidade", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    void updateFromRequest(AtendimentoRequest request, @MappingTarget Atendimento entity);

    AtendimentoResponse toResponse(Atendimento entity);
}
