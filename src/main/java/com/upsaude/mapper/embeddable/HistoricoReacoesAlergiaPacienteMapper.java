package com.upsaude.mapper.embeddable;

import com.upsaude.api.request.embeddable.HistoricoReacoesAlergiaPacienteRequest;
import com.upsaude.api.response.embeddable.HistoricoReacoesAlergiaPacienteResponse;
import com.upsaude.entity.embeddable.HistoricoReacoesAlergiaPaciente;
import com.upsaude.dto.embeddable.HistoricoReacoesAlergiaPacienteDTO;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class)
public interface HistoricoReacoesAlergiaPacienteMapper {
    HistoricoReacoesAlergiaPaciente toEntity(HistoricoReacoesAlergiaPacienteRequest request);
    HistoricoReacoesAlergiaPacienteResponse toResponse(HistoricoReacoesAlergiaPaciente entity);
    void updateFromRequest(HistoricoReacoesAlergiaPacienteRequest request, @MappingTarget HistoricoReacoesAlergiaPaciente entity);

    // Mapeamento entre DTO e Entity
    HistoricoReacoesAlergiaPaciente toEntity(com.upsaude.dto.embeddable.HistoricoReacoesAlergiaPacienteDTO dto);
    com.upsaude.dto.embeddable.HistoricoReacoesAlergiaPacienteDTO toDTO(HistoricoReacoesAlergiaPaciente entity);
    void updateFromDTO(com.upsaude.dto.embeddable.HistoricoReacoesAlergiaPacienteDTO dto, @MappingTarget HistoricoReacoesAlergiaPaciente entity);
}
