package com.upsaude.mapper.vacina;

import com.upsaude.api.request.vacina.FabricantesVacinaRequest;
import com.upsaude.api.response.vacina.FabricantesVacinaResponse;
import com.upsaude.dto.FabricantesVacinaDTO;
import com.upsaude.entity.vacina.FabricantesVacina;
import com.upsaude.entity.paciente.Endereco;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

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
