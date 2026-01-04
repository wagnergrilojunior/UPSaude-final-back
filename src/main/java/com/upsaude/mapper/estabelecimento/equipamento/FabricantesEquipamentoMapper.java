package com.upsaude.mapper.estabelecimento.equipamento;

import com.upsaude.api.request.estabelecimento.equipamento.FabricantesEquipamentoRequest;
import com.upsaude.api.response.estabelecimento.equipamento.FabricantesEquipamentoResponse;
import com.upsaude.entity.estabelecimento.equipamento.FabricantesEquipamento;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.geral.EnderecoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {EnderecoMapper.class})
public interface FabricantesEquipamentoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "endereco", ignore = true)
    FabricantesEquipamento fromRequest(FabricantesEquipamentoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "endereco", ignore = true)
    void updateFromRequest(FabricantesEquipamentoRequest request, @MappingTarget FabricantesEquipamento entity);

    FabricantesEquipamentoResponse toResponse(FabricantesEquipamento entity);
}
