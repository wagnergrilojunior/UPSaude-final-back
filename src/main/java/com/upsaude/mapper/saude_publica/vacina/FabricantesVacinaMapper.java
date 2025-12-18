package com.upsaude.mapper.saude_publica.vacina;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.saude_publica.vacina.FabricantesVacinaRequest;
import com.upsaude.api.response.saude_publica.vacina.FabricantesVacinaResponse;
import com.upsaude.dto.saude_publica.vacina.FabricantesVacinaDTO;
import com.upsaude.entity.saude_publica.vacina.FabricantesVacina;
import com.upsaude.mapper.EntityMapper;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.geral.EnderecoMapper;

@Mapper(config = MappingConfig.class, uses = {EnderecoMapper.class})
public interface FabricantesVacinaMapper extends EntityMapper<FabricantesVacina, FabricantesVacinaDTO> {

    @Mapping(target = "active", ignore = true)
    FabricantesVacina toEntity(FabricantesVacinaDTO dto);

    FabricantesVacinaDTO toDTO(FabricantesVacina entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "endereco", ignore = true)
    FabricantesVacina fromRequest(FabricantesVacinaRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "endereco", ignore = true)
    void updateFromRequest(FabricantesVacinaRequest request, @MappingTarget FabricantesVacina entity);

    FabricantesVacinaResponse toResponse(FabricantesVacina entity);
}
