package com.upsaude.mapper.clinica.prontuario;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.clinica.prontuario.HistoricoClinicoRequest;
import com.upsaude.api.response.clinica.prontuario.HistoricoClinicoResponse;
import com.upsaude.dto.clinica.prontuario.HistoricoClinicoDTO;
import com.upsaude.entity.clinica.prontuario.HistoricoClinico;
import com.upsaude.mapper.EntityMapper;
import com.upsaude.mapper.agendamento.AgendamentoMapper;
import com.upsaude.mapper.clinica.atendimento.AtendimentoMapper;
import com.upsaude.mapper.clinica.cirurgia.CirurgiaMapper;
import com.upsaude.mapper.clinica.exame.ExamesMapper;
import com.upsaude.mapper.clinica.medicacao.ReceitasMedicasMapper;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.paciente.PacienteMapper;
import com.upsaude.mapper.profissional.ProfissionaisSaudeMapper;

@Mapper(config = MappingConfig.class, uses = {AgendamentoMapper.class, AtendimentoMapper.class, CirurgiaMapper.class, ExamesMapper.class, PacienteMapper.class, ProfissionaisSaudeMapper.class, ReceitasMedicasMapper.class})
public interface HistoricoClinicoMapper extends EntityMapper<HistoricoClinico, HistoricoClinicoDTO> {

    @Mapping(target = "active", ignore = true)
    HistoricoClinico toEntity(HistoricoClinicoDTO dto);

    HistoricoClinicoDTO toDTO(HistoricoClinico entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "agendamento", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "cirurgia", ignore = true)
    @Mapping(target = "exame", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    @Mapping(target = "receita", ignore = true)
    HistoricoClinico fromRequest(HistoricoClinicoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "agendamento", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "cirurgia", ignore = true)
    @Mapping(target = "exame", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    @Mapping(target = "receita", ignore = true)
    void updateFromRequest(HistoricoClinicoRequest request, @MappingTarget HistoricoClinico entity);

    HistoricoClinicoResponse toResponse(HistoricoClinico entity);
}
