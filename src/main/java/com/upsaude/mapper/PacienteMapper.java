package com.upsaude.mapper;

import com.upsaude.api.request.PacienteRequest;
import com.upsaude.api.response.PacienteResponse;
import com.upsaude.dto.PacienteDTO;
import com.upsaude.entity.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper para conversões de Paciente.
 * Entity ↔ DTO ↔ Request/Response
 */
@Mapper(config = MappingConfig.class, uses = {ConvenioMapper.class, DadosClinicosBasicosMapper.class, DadosSociodemograficosMapper.class, IntegracaoGovMapper.class, LGPDConsentimentoMapper.class, ResponsavelLegalMapper.class})
public interface PacienteMapper extends EntityMapper<Paciente, PacienteDTO> {

    /**
     * Converte DTO para Entity.
     * O campo 'active' é ignorado (gerenciado pelo sistema).
     */
    @Mapping(target = "active", ignore = true)
    Paciente toEntity(PacienteDTO dto);

    /**
     * Converte Entity para DTO.
     */
    PacienteDTO toDTO(Paciente entity);

    /**
     * Converte Request para Entity.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) e listas devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "convenio", ignore = true)
    @Mapping(target = "dadosClinicosBasicos", ignore = true)
    @Mapping(target = "dadosSociodemograficos", ignore = true)
    @Mapping(target = "integracaoGov", ignore = true)
    @Mapping(target = "lgpdConsentimento", ignore = true)
    @Mapping(target = "responsavelLegal", ignore = true)
    @Mapping(target = "enderecos", ignore = true)
    @Mapping(target = "doencas", ignore = true)
    @Mapping(target = "alergias", ignore = true)
    @Mapping(target = "deficiencias", ignore = true)
    @Mapping(target = "medicacoes", ignore = true)
    Paciente fromRequest(PacienteRequest request);

    /**
     * Atualiza Entity existente com dados do Request.
     * Os campos 'id', 'createdAt', 'updatedAt', 'active' são ignorados.
     * Relacionamentos (UUID) e listas devem ser tratados manualmente no Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "convenio", ignore = true)
    @Mapping(target = "dadosClinicosBasicos", ignore = true)
    @Mapping(target = "dadosSociodemograficos", ignore = true)
    @Mapping(target = "integracaoGov", ignore = true)
    @Mapping(target = "lgpdConsentimento", ignore = true)
    @Mapping(target = "responsavelLegal", ignore = true)
    @Mapping(target = "enderecos", ignore = true)
    @Mapping(target = "doencas", ignore = true)
    @Mapping(target = "alergias", ignore = true)
    @Mapping(target = "deficiencias", ignore = true)
    @Mapping(target = "medicacoes", ignore = true)
    void updateFromRequest(PacienteRequest request, @MappingTarget Paciente entity);

    /**
     * Converte Entity para Response.
     */
    PacienteResponse toResponse(Paciente entity);
}
