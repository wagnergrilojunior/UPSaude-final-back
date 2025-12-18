package com.upsaude.mapper.prontuario;

import com.upsaude.api.request.prontuario.HistoricoClinicoRequest;
import com.upsaude.api.response.prontuario.HistoricoClinicoResponse;
import com.upsaude.dto.HistoricoClinicoDTO;
import com.upsaude.entity.prontuario.HistoricoClinico;
import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.entity.atendimento.Atendimento;
import com.upsaude.entity.cirurgia.Cirurgia;
import com.upsaude.entity.exame.Exames;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.medicacao.ReceitasMedicas;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

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
