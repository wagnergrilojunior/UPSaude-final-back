package com.upsaude.mapper.atendimento;
import com.upsaude.api.response.profissional.MedicosResponse;
import com.upsaude.api.response.agendamento.AgendamentoResponse;
import com.upsaude.api.response.paciente.ResponsavelLegalResponse;
import com.upsaude.api.response.paciente.PacienteResponse;

import com.upsaude.api.request.atendimento.CheckInAtendimentoRequest;
import com.upsaude.api.response.atendimento.CheckInAtendimentoResponse;
import com.upsaude.dto.CheckInAtendimentoDTO;
import com.upsaude.entity.atendimento.CheckInAtendimento;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {AgendamentoMapper.class, AtendimentoMapper.class, PacienteMapper.class})
public interface CheckInAtendimentoMapper extends EntityMapper<CheckInAtendimento, CheckInAtendimentoDTO> {

    @Mapping(target = "active", ignore = true)
    CheckInAtendimento toEntity(CheckInAtendimentoDTO dto);

    CheckInAtendimentoDTO toDTO(CheckInAtendimento entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "agendamento", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    CheckInAtendimento fromRequest(CheckInAtendimentoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "agendamento", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    void updateFromRequest(CheckInAtendimentoRequest request, @MappingTarget CheckInAtendimento entity);

    // Evita recursão infinita na serialização:
    // - CheckInAtendimentoResponse -> AgendamentoResponse -> ... -> MedicosResponse -> medicoEstabelecimento -> MedicosResponse ...
    // - CheckInAtendimentoResponse -> AgendamentoResponse -> checkIns -> CheckInAtendimentoResponse ...
    //
    // Para manter o endpoint estável, retornamos o check-in sem objetos aninhados (agendamento/atendimento/paciente),
    // já que `PacienteResponse` também pode entrar em ciclos (ex.: ResponsavelLegalResponse -> PacienteResponse ...).
    @Mapping(target = "agendamento", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    CheckInAtendimentoResponse toResponse(CheckInAtendimento entity);
}
