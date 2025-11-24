package com.upsaude.mapper;

import com.upsaude.api.request.ReceitasMedicasRequest;
import com.upsaude.api.response.ReceitasMedicasResponse;
import com.upsaude.dto.ReceitasMedicasDTO;
import com.upsaude.entity.ReceitasMedicas;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface ReceitasMedicasMapper extends EntityMapper<ReceitasMedicas, ReceitasMedicasDTO> {

    @Mapping(target = "tenant", ignore = true)
    ReceitasMedicas toEntity(ReceitasMedicasDTO dto);

    ReceitasMedicasDTO toDTO(ReceitasMedicas entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    ReceitasMedicas fromRequest(ReceitasMedicasRequest request);

    ReceitasMedicasResponse toResponse(ReceitasMedicas entity);
}

