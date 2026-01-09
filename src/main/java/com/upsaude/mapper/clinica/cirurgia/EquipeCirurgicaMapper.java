package com.upsaude.mapper.clinica.cirurgia;

import com.upsaude.api.request.clinica.cirurgia.EquipeCirurgicaRequest;
import com.upsaude.api.response.clinica.cirurgia.EquipeCirurgicaResponse;
import com.upsaude.api.response.clinica.cirurgia.EquipeCirurgicaMedicoResponse;
import com.upsaude.api.response.clinica.cirurgia.EquipeCirurgicaProfissionalResponse;
import com.upsaude.entity.clinica.cirurgia.EquipeCirurgica;
import com.upsaude.entity.clinica.cirurgia.EquipeCirurgicaMedico;
import com.upsaude.entity.clinica.cirurgia.EquipeCirurgicaProfissional;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(config = MappingConfig.class, uses = {EquipeCirurgicaMedicoMapper.class, EquipeCirurgicaProfissionalMapper.class})
public interface EquipeCirurgicaMapper  {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "cirurgia", ignore = true)
    @Mapping(target = "medicos", ignore = true)
    @Mapping(target = "profissionais", ignore = true)
    EquipeCirurgica fromRequest(EquipeCirurgicaRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "cirurgia", ignore = true)
    @Mapping(target = "medicos", ignore = true)
    @Mapping(target = "profissionais", ignore = true)
    void updateFromRequest(EquipeCirurgicaRequest request, @MappingTarget EquipeCirurgica entity);

    @Mapping(target = "tenantId", source = "tenant.id")
    @Mapping(target = "estabelecimentoId", source = "estabelecimento.id")
    @Mapping(target = "profissionais", source = "profissionais", qualifiedByName = "mapProfissionaisEquipe")
    @Mapping(target = "medicos", source = "medicos", qualifiedByName = "mapMedicosEquipe")
    EquipeCirurgicaResponse toResponse(EquipeCirurgica entity);

    @Named("mapProfissionaisEquipe")
    default List<EquipeCirurgicaProfissionalResponse> mapProfissionaisEquipe(List<EquipeCirurgicaProfissional> profissionais) {
        if (profissionais == null || profissionais.isEmpty()) {
            return new java.util.ArrayList<>();
        }
        EquipeCirurgicaProfissionalMapper mapper = Mappers.getMapper(EquipeCirurgicaProfissionalMapper.class);
        return profissionais.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Named("mapMedicosEquipe")
    default List<EquipeCirurgicaMedicoResponse> mapMedicosEquipe(List<EquipeCirurgicaMedico> medicos) {
        if (medicos == null || medicos.isEmpty()) {
            return new java.util.ArrayList<>();
        }
        EquipeCirurgicaMedicoMapper mapper = Mappers.getMapper(EquipeCirurgicaMedicoMapper.class);
        return medicos.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}
