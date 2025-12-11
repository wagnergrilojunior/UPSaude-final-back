package com.upsaude.mapper;

import com.upsaude.api.request.HistoricoClinicoRequest;
import com.upsaude.api.response.HistoricoClinicoResponse;
import com.upsaude.dto.HistoricoClinicoDTO;
import com.upsaude.entity.HistoricoClinico;
import com.upsaude.entity.Agendamento;
import com.upsaude.entity.Atendimento;
import com.upsaude.entity.Cirurgia;
import com.upsaude.entity.Exames;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.entity.ReceitasMedicas;
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
