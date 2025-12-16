package com.upsaude.mapper;

import com.upsaude.api.request.MedicosRequest;
import com.upsaude.api.response.MedicosResponse;
import com.upsaude.dto.MedicosDTO;
import com.upsaude.entity.Medicos;
import com.upsaude.entity.EspecialidadesMedicas;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {EspecialidadesMedicasMapper.class, MedicoEstabelecimentoMapper.class, com.upsaude.mapper.embeddable.DadosPessoaisMedicoMapper.class, com.upsaude.mapper.embeddable.RegistroProfissionalMedicoMapper.class, com.upsaude.mapper.embeddable.FormacaoMedicoMapper.class, com.upsaude.mapper.embeddable.ContatoMedicoMapper.class})
public interface MedicosMapper extends EntityMapper<Medicos, MedicosDTO> {

    @Mapping(target = "active", ignore = true)
    Medicos toEntity(MedicosDTO dto);

    MedicosDTO toDTO(Medicos entity);

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
