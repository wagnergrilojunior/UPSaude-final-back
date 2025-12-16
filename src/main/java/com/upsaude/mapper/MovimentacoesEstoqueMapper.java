package com.upsaude.mapper;

import com.upsaude.api.request.MovimentacoesEstoqueRequest;
import com.upsaude.api.response.MovimentacoesEstoqueResponse;
import com.upsaude.dto.MovimentacoesEstoqueDTO;
import com.upsaude.entity.MovimentacoesEstoque;
import com.upsaude.entity.EstoquesVacina;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {EstoquesVacinaMapper.class})
public interface MovimentacoesEstoqueMapper extends EntityMapper<MovimentacoesEstoque, MovimentacoesEstoqueDTO> {

    @Mapping(target = "active", ignore = true)
    MovimentacoesEstoque toEntity(MovimentacoesEstoqueDTO dto);

    MovimentacoesEstoqueDTO toDTO(MovimentacoesEstoque entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estoqueVacina", ignore = true)
    MovimentacoesEstoque fromRequest(MovimentacoesEstoqueRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estoqueVacina", ignore = true)
    void updateFromRequest(MovimentacoesEstoqueRequest request, @MappingTarget MovimentacoesEstoque entity);

    MovimentacoesEstoqueResponse toResponse(MovimentacoesEstoque entity);
}
