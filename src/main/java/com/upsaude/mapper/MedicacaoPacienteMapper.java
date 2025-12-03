package com.upsaude.mapper;

import com.upsaude.api.request.MedicacaoPacienteRequest;
import com.upsaude.api.response.MedicacaoPacienteResponse;
import com.upsaude.dto.MedicacaoPacienteDTO;
import com.upsaude.entity.MedicacaoPaciente;
import com.upsaude.entity.CidDoencas;
import com.upsaude.entity.Medicacao;
import com.upsaude.entity.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de MedicacaoPaciente.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {CidDoencasMapper.class, MedicacaoMapper.class, PacienteMapper.class})
public interface MedicacaoPacienteMapper extends EntityMapper<MedicacaoPaciente, MedicacaoPacienteDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    MedicacaoPaciente toEntity(MedicacaoPacienteDTO dto);

    /**
     * Converte Entity para DTO.
     */
    MedicacaoPacienteDTO toDTO(MedicacaoPaciente entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "cidRelacionado", ignore = true)
    @Mapping(target = "medicacao", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    MedicacaoPaciente fromRequest(MedicacaoPacienteRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "cidRelacionado", ignore = true)
    @Mapping(target = "medicacao", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    void updateFromRequest(MedicacaoPacienteRequest request, @MappingTarget MedicacaoPaciente entity);

    /**
     * Converte Entity para Response.
     */
    MedicacaoPacienteResponse toResponse(MedicacaoPaciente entity);
}
