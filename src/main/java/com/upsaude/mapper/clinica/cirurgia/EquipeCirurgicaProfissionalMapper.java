package com.upsaude.mapper.clinica.cirurgia;

import com.upsaude.api.request.clinica.cirurgia.EquipeCirurgicaProfissionalRequest;
import com.upsaude.api.response.clinica.cirurgia.EquipeCirurgicaProfissionalResponse;
import com.upsaude.api.response.clinica.atendimento.ProfissionalAtendimentoResponse;
import com.upsaude.entity.clinica.cirurgia.EquipeCirurgicaProfissional;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.mapper.clinica.atendimento.AtendimentoMapper;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(config = MappingConfig.class, uses = {AtendimentoMapper.class})
public interface EquipeCirurgicaProfissionalMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "equipeCirurgica", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    EquipeCirurgicaProfissional fromRequest(EquipeCirurgicaProfissionalRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "equipeCirurgica", ignore = true)
    @Mapping(target = "profissional", ignore = true)
    void updateFromRequest(EquipeCirurgicaProfissionalRequest request, @MappingTarget EquipeCirurgicaProfissional entity);

    @Mapping(target = "tenantId", source = "tenant.id")
    @Mapping(target = "estabelecimentoId", source = "estabelecimento.id")
    @Mapping(target = "profissional", source = "profissional", qualifiedByName = "mapProfissionalSimplificadoEquipeProfissional")
    EquipeCirurgicaProfissionalResponse toResponse(EquipeCirurgicaProfissional entity);

    @Named("mapProfissionalSimplificadoEquipeProfissional")
    default ProfissionalAtendimentoResponse mapProfissionalSimplificadoEquipeProfissional(ProfissionaisSaude profissional) {
        if (profissional == null) {
            return null;
        }
        AtendimentoMapper mapper = Mappers.getMapper(AtendimentoMapper.class);
        return mapper.mapProfissionalSimplificado(profissional);
    }
}

