package com.upsaude.mapper.estabelecimento.equipamento;

import com.upsaude.api.request.estabelecimento.equipamento.EquipamentosRequest;
import com.upsaude.api.response.estabelecimento.equipamento.EquipamentosResponse;
import com.upsaude.dto.estabelecimento.equipamento.EquipamentosDTO;
import com.upsaude.entity.estabelecimento.equipamento.Equipamentos;
import com.upsaude.entity.estabelecimento.equipamento.FabricantesEquipamento;
import com.upsaude.mapper.EntityMapper;
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
