package com.upsaude.mapper;

import com.upsaude.api.request.EquipamentosEstabelecimentoRequest;
import com.upsaude.api.response.EquipamentosEstabelecimentoResponse;
import com.upsaude.dto.EquipamentosEstabelecimentoDTO;
import com.upsaude.entity.EquipamentosEstabelecimento;
import com.upsaude.entity.Equipamentos;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {EquipamentosMapper.class, EstabelecimentosMapper.class})
public interface EquipamentosEstabelecimentoMapper extends EntityMapper<EquipamentosEstabelecimento, EquipamentosEstabelecimentoDTO> {

    @Mapping(target = "active", ignore = true)
    EquipamentosEstabelecimento toEntity(EquipamentosEstabelecimentoDTO dto);

    EquipamentosEstabelecimentoDTO toDTO(EquipamentosEstabelecimento entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "equipamento", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    EquipamentosEstabelecimento fromRequest(EquipamentosEstabelecimentoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "equipamento", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    void updateFromRequest(EquipamentosEstabelecimentoRequest request, @MappingTarget EquipamentosEstabelecimento entity);

    EquipamentosEstabelecimentoResponse toResponse(EquipamentosEstabelecimento entity);
}
