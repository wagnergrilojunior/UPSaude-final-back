package com.upsaude.mapper;

import com.upsaude.api.request.MovimentacoesEstoqueRequest;
import com.upsaude.api.response.MovimentacoesEstoqueResponse;
import com.upsaude.dto.MovimentacoesEstoqueDTO;
import com.upsaude.entity.MovimentacoesEstoque;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface MovimentacoesEstoqueMapper extends EntityMapper<MovimentacoesEstoque, MovimentacoesEstoqueDTO> {

    @Mapping(target = "tenant", ignore = true)
    MovimentacoesEstoque toEntity(MovimentacoesEstoqueDTO dto);

    MovimentacoesEstoqueDTO toDTO(MovimentacoesEstoque entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    MovimentacoesEstoque fromRequest(MovimentacoesEstoqueRequest request);

    MovimentacoesEstoqueResponse toResponse(MovimentacoesEstoque entity);
}

