package com.upsaude.mapper.saude_publica.visita;

import com.upsaude.api.request.saude_publica.visita.VisitasDomiciliaresRequest;
import com.upsaude.api.response.saude_publica.visita.VisitasDomiciliaresResponse;
import com.upsaude.dto.saude_publica.visita.VisitasDomiciliaresDTO;
import com.upsaude.entity.saude_publica.visita.VisitasDomiciliares;
import com.upsaude.mapper.EntityMapper;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.profissional.equipe.EquipeSaudeMapper;
import com.upsaude.mapper.paciente.PacienteMapper;
import com.upsaude.mapper.profissional.ProfissionaisSaudeMapper;
import com.upsaude.mapper.estabelecimento.EstabelecimentosMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {EquipeSaudeMapper.class, PacienteMapper.class, ProfissionaisSaudeMapper.class, EstabelecimentosMapper.class})
public interface VisitasDomiciliaresMapper extends EntityMapper<VisitasDomiciliares, VisitasDomiciliaresDTO> {

    @Mapping(target = "active", ignore = true)
    VisitasDomiciliares toEntity(VisitasDomiciliaresDTO dto);

    VisitasDomiciliaresDTO toDTO(VisitasDomiciliares entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "equipeSaude", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    VisitasDomiciliares fromRequest(VisitasDomiciliaresRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "equipeSaude", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    void updateFromRequest(VisitasDomiciliaresRequest request, @MappingTarget VisitasDomiciliares entity);

    VisitasDomiciliaresResponse toResponse(VisitasDomiciliares entity);
}
