package com.upsaude.mapper.clinica.prontuario;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import com.upsaude.api.request.clinica.prontuario.DoencaPacienteRequest;
import com.upsaude.api.response.clinica.prontuario.DoencaPacienteResponse;
import com.upsaude.entity.clinica.prontuario.DoencaPaciente;
import com.upsaude.entity.referencia.Ciap2;
import com.upsaude.entity.referencia.cid.Cid10Subcategorias;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.diagnostico.Ciap2Mapper;
import com.upsaude.mapper.referencia.cid.Cid10SubcategoriaMapper;
import com.upsaude.repository.referencia.Ciap2Repository;
import com.upsaude.repository.referencia.cid.Cid10SubcategoriasRepository;

@Mapper(config = MappingConfig.class, uses = { Cid10SubcategoriaMapper.class, Ciap2Mapper.class })
public abstract class DoencaPacienteMapper {

    @Autowired
    protected Cid10SubcategoriasRepository cid10Repository;

    @Autowired
    protected Ciap2Repository ciap2Repository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "prontuario", ignore = true)
    @Mapping(target = "diagnostico", source = "diagnostico")
    @Mapping(target = "ciap2", source = "ciap2")
    public abstract DoencaPaciente fromRequest(DoencaPacienteRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "prontuario", ignore = true)
    @Mapping(target = "diagnostico", source = "diagnostico")
    @Mapping(target = "ciap2", source = "ciap2")
    public abstract void updateFromRequest(DoencaPacienteRequest request, @MappingTarget DoencaPaciente entity);

    public abstract DoencaPacienteResponse toResponse(DoencaPaciente entity);

    protected Cid10Subcategorias mapCid10(UUID id) {
        if (id == null)
            return null;
        return cid10Repository.findById(id).orElse(null);
    }

    protected Ciap2 mapCiap2(UUID id) {
        if (id == null)
            return null;
        return ciap2Repository.findById(id).orElse(null);
    }
}
