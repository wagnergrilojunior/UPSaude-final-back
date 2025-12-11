package com.upsaude.mapper;

import com.upsaude.api.request.EquipamentosRequest;
import com.upsaude.api.response.EquipamentosResponse;
import com.upsaude.dto.EquipamentosDTO;
import com.upsaude.entity.Equipamentos;
import com.upsaude.entity.FabricantesEquipamento;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {FabricantesEquipamentoMapper.class})
public interface EquipamentosMapper extends EntityMapper<Equipamentos, EquipamentosDTO> {

    @Mapping(target = "active", ignore = true)
    Equipamentos toEntity(EquipamentosDTO dto);

    EquipamentosDTO toDTO(Equipamentos entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "fabricante", ignore = true)
    Equipamentos fromRequest(EquipamentosRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "fabricante", ignore = true)
    void updateFromRequest(EquipamentosRequest request, @MappingTarget Equipamentos entity);

    EquipamentosResponse toResponse(Equipamentos entity);
}
