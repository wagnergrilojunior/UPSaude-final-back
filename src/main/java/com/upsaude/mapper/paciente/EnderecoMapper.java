package com.upsaude.mapper.paciente;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.paciente.EnderecoRequest;
import com.upsaude.api.response.paciente.EnderecoResponse;
import com.upsaude.dto.paciente.EnderecoDTO;
import com.upsaude.entity.paciente.Endereco;
import com.upsaude.mapper.EntityMapper;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.referencia.geografico.CidadesMapper;
import com.upsaude.mapper.referencia.geografico.EstadosMapper;

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
