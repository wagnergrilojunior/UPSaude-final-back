package com.upsaude.mapper;

import com.upsaude.api.request.AtendimentoRequest;
import com.upsaude.api.response.AtendimentoResponse;
import com.upsaude.dto.AtendimentoDTO;
import com.upsaude.entity.Atendimento;
import com.upsaude.entity.CidDoencas;
import com.upsaude.entity.Convenio;
import com.upsaude.entity.EquipeSaude;
import com.upsaude.entity.EspecialidadesMedicas;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de Atendimento.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {CidDoencasMapper.class, ConvenioMapper.class, EquipeSaudeMapper.class, EspecialidadesMedicasMapper.class, PacienteMapper.class, ProfissionaisSaudeMapper.class, com.upsaude.mapper.embeddable.InformacoesAtendimentoMapper.class, com.upsaude.mapper.embeddable.AnamneseAtendimentoMapper.class, com.upsaude.mapper.embeddable.DiagnosticoAtendimentoMapper.class, com.upsaude.mapper.embeddable.ProcedimentosRealizadosAtendimentoMapper.class, com.upsaude.mapper.embeddable.ClassificacaoRiscoAtendimentoMapper.class})
public interface AtendimentoMapper extends EntityMapper<Atendimento, AtendimentoDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    Atendimento toEntity(AtendimentoDTO dto);

    /**
     * Converte Entity para DTO.
     */
    AtendimentoDTO toDTO(Atendimento entity);

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
    @Mapping(target = "convenio", ignore = true)
    @Mapping(target = "equipeSaude", ignore = true)
    @Mapping(target = "especialidade", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    Atendimento fromRequest(AtendimentoRequest request);

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
    @Mapping(target = "convenio", ignore = true)
    @Mapping(target = "equipeSaude", ignore = true)
    @Mapping(target = "especialidade", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    void updateFromRequest(AtendimentoRequest request, @MappingTarget Atendimento entity);

    /**
     * Converte Entity para Response.
     */
    AtendimentoResponse toResponse(Atendimento entity);
}
