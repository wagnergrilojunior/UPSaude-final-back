package com.upsaude.mapper;

import com.upsaude.api.request.MedicoEstabelecimentoRequest;
import com.upsaude.api.response.MedicoEstabelecimentoResponse;
import com.upsaude.dto.MedicoEstabelecimentoDTO;
import com.upsaude.entity.MedicoEstabelecimento;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.Medicos;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {EstabelecimentosMapper.class})
public interface MedicoEstabelecimentoMapper extends EntityMapper<MedicoEstabelecimento, MedicoEstabelecimentoDTO> {

    @Mapping(target = "active", ignore = true)
    MedicoEstabelecimento toEntity(MedicoEstabelecimentoDTO dto);

    MedicoEstabelecimentoDTO toDTO(MedicoEstabelecimento entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "medico", ignore = true)
    MedicoEstabelecimento fromRequest(MedicoEstabelecimentoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "medico", ignore = true)
    void updateFromRequest(MedicoEstabelecimentoRequest request, @MappingTarget MedicoEstabelecimento entity);

    @Mapping(target = "medico", ignore = true)
    MedicoEstabelecimentoResponse toResponse(MedicoEstabelecimento entity);
}
