package com.upsaude.mapper;

import com.upsaude.api.request.ExamesRequest;
import com.upsaude.api.response.ExamesResponse;
import com.upsaude.dto.ExamesDTO;
import com.upsaude.entity.Exames;
import com.upsaude.entity.Atendimento;
import com.upsaude.entity.CatalogoExames;
import com.upsaude.entity.Consultas;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.Medicos;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de Exames.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {AtendimentoMapper.class, CatalogoExamesMapper.class, ConsultasMapper.class, EstabelecimentosMapper.class, MedicosMapper.class, PacienteMapper.class, ProfissionaisSaudeMapper.class})
public interface ExamesMapper extends EntityMapper<Exames, ExamesDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    Exames toEntity(ExamesDTO dto);

    /**
     * Converte Entity para DTO.
     */
    ExamesDTO toDTO(Exames entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "catalogoExame", ignore = true)
    @Mapping(target = "consulta", ignore = true)
    @Mapping(target = "estabelecimentoRealizador", ignore = true)
    @Mapping(target = "medicoResponsavel", ignore = true)
    @Mapping(target = "medicoSolicitante", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissionalResponsavel", ignore = true)
    @Mapping(target = "profissionalSolicitante", ignore = true)
    Exames fromRequest(ExamesRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "catalogoExame", ignore = true)
    @Mapping(target = "consulta", ignore = true)
    @Mapping(target = "estabelecimentoRealizador", ignore = true)
    @Mapping(target = "medicoResponsavel", ignore = true)
    @Mapping(target = "medicoSolicitante", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissionalResponsavel", ignore = true)
    @Mapping(target = "profissionalSolicitante", ignore = true)
    void updateFromRequest(ExamesRequest request, @MappingTarget Exames entity);

    /**
     * Converte Entity para Response.
     */
    ExamesResponse toResponse(Exames entity);
}
