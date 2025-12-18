package com.upsaude.mapper.visita;

import com.upsaude.api.request.visita.VisitasDomiciliaresRequest;
import com.upsaude.api.response.visita.VisitasDomiciliaresResponse;
import com.upsaude.dto.VisitasDomiciliaresDTO;
import com.upsaude.entity.visita.VisitasDomiciliares;
import com.upsaude.mapper.config.MappingConfig;
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
