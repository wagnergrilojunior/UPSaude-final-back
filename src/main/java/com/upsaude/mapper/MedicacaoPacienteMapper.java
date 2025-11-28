package com.upsaude.mapper;

import com.upsaude.api.request.MedicacaoPacienteRequest;
import com.upsaude.api.response.MedicacaoPacienteResponse;
import com.upsaude.dto.MedicacaoPacienteDTO;
import com.upsaude.entity.MedicacaoPaciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper para conversão entre entidades, DTOs, Requests e Responses de Medicações de Paciente.
 *
 * @author UPSaúde
 */
@Mapper(config = MappingConfig.class)
public interface MedicacaoPacienteMapper extends EntityMapper<MedicacaoPaciente, MedicacaoPacienteDTO> {

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "medicacao", ignore = true)
    @Mapping(target = "cidRelacionado", ignore = true)
    MedicacaoPaciente toEntity(MedicacaoPacienteDTO dto);

    MedicacaoPacienteDTO toDTO(MedicacaoPaciente entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "medicacao", ignore = true)
    @Mapping(target = "cidRelacionado", ignore = true)
    MedicacaoPaciente fromRequest(MedicacaoPacienteRequest request);

    @Mapping(target = "pacienteId", source = "paciente.id")
    @Mapping(target = "medicacaoId", source = "medicacao.id")
    @Mapping(target = "medicacaoNomeComercial", source = "medicacao.nomeComercial")
    @Mapping(target = "medicacaoPrincipioAtivo", source = "medicacao.principioAtivo")
    @Mapping(target = "cidRelacionadoId", source = "cidRelacionado.id")
    @Mapping(target = "cidRelacionadoCodigo", source = "cidRelacionado.codigo")
    MedicacaoPacienteResponse toResponse(MedicacaoPaciente entity);
}

