package com.upsaude.mapper;

import com.upsaude.api.request.EstoquesVacinaRequest;
import com.upsaude.api.response.EstoquesVacinaResponse;
import com.upsaude.dto.EstoquesVacinaDTO;
import com.upsaude.entity.EstoquesVacina;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.Vacinas;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {EstabelecimentosMapper.class, VacinasMapper.class})
public interface EstoquesVacinaMapper extends EntityMapper<EstoquesVacina, EstoquesVacinaDTO> {

    @Mapping(target = "active", ignore = true)
    EstoquesVacina toEntity(EstoquesVacinaDTO dto);

    EstoquesVacinaDTO toDTO(EstoquesVacina entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "vacina", ignore = true)
    EstoquesVacina fromRequest(EstoquesVacinaRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "vacina", ignore = true)
    void updateFromRequest(EstoquesVacinaRequest request, @MappingTarget EstoquesVacina entity);

    EstoquesVacinaResponse toResponse(EstoquesVacina entity);
}
