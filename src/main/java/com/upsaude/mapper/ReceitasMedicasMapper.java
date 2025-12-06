package com.upsaude.mapper;

import com.upsaude.api.request.ReceitasMedicasRequest;
import com.upsaude.api.response.ReceitasMedicasResponse;
import com.upsaude.dto.ReceitasMedicasDTO;
import com.upsaude.entity.ReceitasMedicas;
import com.upsaude.entity.CidDoencas;
import com.upsaude.entity.Medicos;
import com.upsaude.entity.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de ReceitasMedicas.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {CidDoencasMapper.class, MedicosMapper.class, PacienteMapper.class})
public interface ReceitasMedicasMapper extends EntityMapper<ReceitasMedicas, ReceitasMedicasDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    ReceitasMedicas toEntity(ReceitasMedicasDTO dto);

    /**
     * Converte Entity para DTO.
     */
    ReceitasMedicasDTO toDTO(ReceitasMedicas entity);

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
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "medicacoes", ignore = true)
    ReceitasMedicas fromRequest(ReceitasMedicasRequest request);

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
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "medicacoes", ignore = true)
    void updateFromRequest(ReceitasMedicasRequest request, @MappingTarget ReceitasMedicas entity);

    /**
     * Converte Entity para Response.
     */
    ReceitasMedicasResponse toResponse(ReceitasMedicas entity);
}
