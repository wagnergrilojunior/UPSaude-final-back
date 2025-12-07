package com.upsaude.mapper;

import com.upsaude.api.request.DoencasPacienteRequest;
import com.upsaude.api.response.DoencasPacienteResponse;
import com.upsaude.dto.DoencasPacienteDTO;
import com.upsaude.entity.DoencasPaciente;
import com.upsaude.entity.CidDoencas;
import com.upsaude.entity.Doencas;
import com.upsaude.entity.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de DoencasPaciente.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {CidDoencasMapper.class, DoencasMapper.class, PacienteMapper.class, com.upsaude.mapper.embeddable.DiagnosticoDoencaPacienteMapper.class, com.upsaude.mapper.embeddable.AcompanhamentoDoencaPacienteMapper.class, com.upsaude.mapper.embeddable.TratamentoAtualDoencaPacienteMapper.class})
public interface DoencasPacienteMapper extends EntityMapper<DoencasPaciente, DoencasPacienteDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    DoencasPaciente toEntity(DoencasPacienteDTO dto);

    /**
     * Converte Entity para DTO.
     */
    DoencasPacienteDTO toDTO(DoencasPaciente entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "cidPrincipal", ignore = true)
    @Mapping(target = "doenca", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    DoencasPaciente fromRequest(DoencasPacienteRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "cidPrincipal", ignore = true)
    @Mapping(target = "doenca", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    void updateFromRequest(DoencasPacienteRequest request, @MappingTarget DoencasPaciente entity);

    /**
     * Converte Entity para Response.
     * IMPORTANTE: O campo 'paciente' é ignorado para evitar recursão circular,
     * pois quando PacienteResponse é mapeado, ele já contém a lista de doencas.
     */
    @Mapping(target = "paciente", ignore = true)
    DoencasPacienteResponse toResponse(DoencasPaciente entity);
}
