package com.upsaude.mapper.paciente;

import com.upsaude.api.request.paciente.EnderecoRequest;
import com.upsaude.api.response.paciente.EnderecoResponse;
import com.upsaude.dto.EnderecoDTO;
import com.upsaude.entity.paciente.Endereco;
import com.upsaude.entity.geografico.Cidades;
import com.upsaude.entity.geografico.Estados;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {CidadesMapper.class, EstadosMapper.class})
public interface EnderecoMapper extends EntityMapper<Endereco, EnderecoDTO> {

    @Mapping(target = "active", ignore = true)
    Endereco toEntity(EnderecoDTO dto);

    EnderecoDTO toDTO(Endereco entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "cidade", ignore = true)
    @Mapping(target = "estado", ignore = true)
    Endereco fromRequest(EnderecoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "cidade", ignore = true)
    @Mapping(target = "estado", ignore = true)
    void updateFromRequest(EnderecoRequest request, @MappingTarget Endereco entity);

    EnderecoResponse toResponse(Endereco entity);
}
