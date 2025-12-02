package com.upsaude.mapper;

import com.upsaude.api.request.PacienteRequest;
import com.upsaude.api.response.PacienteResponse;
import com.upsaude.dto.PacienteDTO;
import com.upsaude.entity.Convenio;
import com.upsaude.entity.Endereco;
import com.upsaude.entity.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Mapper(config = MappingConfig.class)
public interface PacienteMapper extends EntityMapper<Paciente, PacienteDTO> {

    @Mapping(target = "enderecos", source = "enderecosIds", qualifiedByName = "enderecosFromIds")
    @Mapping(target = "convenio", source = "convenioId", qualifiedByName = "convenioFromId")
    Paciente toEntity(PacienteDTO dto);

    @Mapping(target = "enderecosIds", source = "enderecos", qualifiedByName = "idsFromEnderecos")
    @Mapping(target = "convenioId", source = "convenio.id")
    PacienteDTO toDTO(Paciente entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "enderecos", source = "enderecosIds", qualifiedByName = "enderecosFromIds")
    @Mapping(target = "convenio", source = "convenioId", qualifiedByName = "convenioFromId")
    Paciente fromRequest(PacienteRequest request);

    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "active", source = "active")
    @Mapping(target = "enderecosIds", source = "enderecos", qualifiedByName = "idsFromEnderecos")
    @Mapping(target = "convenioId", source = "convenio.id")
    PacienteResponse toResponse(Paciente entity);

    @Named("convenioFromId")
    default Convenio convenioFromId(UUID id) {
        if (id == null) return null;
        Convenio c = new Convenio();
        c.setId(id);
        return c;
    }

    @Named("enderecosFromIds")
    default List<Endereco> enderecosFromIds(List<UUID> ids) {
        if (ids == null) return new ArrayList<>();
        List<Endereco> list = new ArrayList<>();
        for (UUID id : ids) {
            Endereco end = new Endereco();
            end.setId(id);
            list.add(end);
        }
        return list;
    }

    @Named("idsFromEnderecos")
    default List<UUID> idsFromEnderecos(List<Endereco> enderecos) {
        if (enderecos == null) return new ArrayList<>();
        List<UUID> ids = new ArrayList<>();
        for (Endereco e : enderecos) {
            ids.add(e.getId());
        }
        return ids;
    }
}

