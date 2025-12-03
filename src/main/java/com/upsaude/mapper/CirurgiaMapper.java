package com.upsaude.mapper;

import com.upsaude.api.request.CirurgiaRequest;
import com.upsaude.api.response.CirurgiaResponse;
import com.upsaude.dto.CirurgiaDTO;
import com.upsaude.entity.Cirurgia;
import com.upsaude.entity.Convenio;
import com.upsaude.entity.EspecialidadesMedicas;
import com.upsaude.entity.Medicos;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de Cirurgia.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {ConvenioMapper.class, EspecialidadesMedicasMapper.class, MedicosMapper.class, PacienteMapper.class, ProfissionaisSaudeMapper.class})
public interface CirurgiaMapper extends EntityMapper<Cirurgia, CirurgiaDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    Cirurgia toEntity(CirurgiaDTO dto);

    /**
     * Converte Entity para DTO.
     */
    CirurgiaDTO toDTO(Cirurgia entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "cirurgiaoPrincipal", ignore = true)
    @Mapping(target = "convenio", ignore = true)
    @Mapping(target = "especialidade", ignore = true)
    @Mapping(target = "medicoCirurgiao", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    Cirurgia fromRequest(CirurgiaRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "cirurgiaoPrincipal", ignore = true)
    @Mapping(target = "convenio", ignore = true)
    @Mapping(target = "especialidade", ignore = true)
    @Mapping(target = "medicoCirurgiao", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    void updateFromRequest(CirurgiaRequest request, @MappingTarget Cirurgia entity);

    /**
     * Converte Entity para Response.
     */
    CirurgiaResponse toResponse(Cirurgia entity);
}
