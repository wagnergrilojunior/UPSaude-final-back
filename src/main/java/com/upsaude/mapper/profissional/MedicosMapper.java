package com.upsaude.mapper.profissional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.profissional.MedicosRequest;
import com.upsaude.api.response.profissional.MedicosResponse;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.estabelecimento.MedicoEstabelecimentoMapper;

@Mapper(config = MappingConfig.class, uses = {EspecialidadesMedicasMapper.class, MedicoEstabelecimentoMapper.class, com.upsaude.mapper.embeddable.DadosPessoaisMedicoMapper.class, com.upsaude.mapper.embeddable.RegistroProfissionalMedicoMapper.class, com.upsaude.mapper.embeddable.FormacaoMedicoMapper.class, com.upsaude.mapper.embeddable.ContatoMedicoMapper.class})
public interface MedicosMapper {

    @Mapping(target = "active", ignore = true)
    Medicos toEntity(MedicosResponse dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "especialidades", ignore = true)
    @Mapping(target = "enderecos", ignore = true)
    @Mapping(target = "medicosEstabelecimentos", ignore = true)
    Medicos fromRequest(MedicosRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "especialidades", ignore = true)
    @Mapping(target = "enderecos", ignore = true)
    @Mapping(target = "medicosEstabelecimentos", ignore = true)
    void updateFromRequest(MedicosRequest request, @MappingTarget Medicos entity);

    MedicosResponse toResponse(Medicos entity);
}
