package com.upsaude.mapper;

import com.upsaude.api.request.MedicosRequest;
import com.upsaude.api.response.MedicosResponse;
import com.upsaude.dto.MedicosDTO;
import com.upsaude.entity.Medicos;
import com.upsaude.entity.EspecialidadesMedicas;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de Medicos.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {EspecialidadesMedicasMapper.class, com.upsaude.mapper.embeddable.DadosPessoaisMedicoMapper.class, com.upsaude.mapper.embeddable.RegistroProfissionalMedicoMapper.class, com.upsaude.mapper.embeddable.FormacaoMedicoMapper.class, com.upsaude.mapper.embeddable.ContatoMedicoMapper.class})
public interface MedicosMapper extends EntityMapper<Medicos, MedicosDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    Medicos toEntity(MedicosDTO dto);

    /**
     * Converte Entity para DTO.
     */
    MedicosDTO toDTO(Medicos entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "especialidade", ignore = true)
    @Mapping(target = "enderecos", ignore = true)
    @Mapping(target = "medicosEstabelecimentos", ignore = true)
    Medicos fromRequest(MedicosRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "especialidade", ignore = true)
    @Mapping(target = "enderecos", ignore = true)
    @Mapping(target = "medicosEstabelecimentos", ignore = true)
    void updateFromRequest(MedicosRequest request, @MappingTarget Medicos entity);

    /**
     * Converte Entity para Response.
     */
    MedicosResponse toResponse(Medicos entity);
}
