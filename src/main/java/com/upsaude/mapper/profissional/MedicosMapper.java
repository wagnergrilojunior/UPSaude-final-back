package com.upsaude.mapper.profissional;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.profissional.MedicosRequest;
import com.upsaude.api.response.profissional.MedicosResponse;
import com.upsaude.entity.estabelecimento.MedicoEstabelecimento;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.embeddable.ContatoMedicoMapper;
import com.upsaude.mapper.embeddable.DadosDemograficosMedicoMapper;
import com.upsaude.mapper.embeddable.DadosPessoaisBasicosMedicoMapper;
import com.upsaude.mapper.embeddable.DocumentosBasicosMedicoMapper;
import com.upsaude.mapper.embeddable.RegistroProfissionalMedicoMapper;
import com.upsaude.mapper.estabelecimento.EstabelecimentosMapper;
import com.upsaude.mapper.geral.EnderecoMapper;

@Mapper(config = MappingConfig.class, uses = {
    EspecialidadeMapper.class,
    DadosPessoaisBasicosMedicoMapper.class,
    DocumentosBasicosMedicoMapper.class,
    DadosDemograficosMedicoMapper.class,
    RegistroProfissionalMedicoMapper.class,
    ContatoMedicoMapper.class,
    EnderecoMapper.class,
    EstabelecimentosMapper.class
})
public interface MedicosMapper {

    EstabelecimentosMapper estabelecimentosMapper = org.mapstruct.factory.Mappers.getMapper(EstabelecimentosMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "enderecoMedico", ignore = true)
    @Mapping(target = "estabelecimentos", ignore = true)
    @Mapping(target = "especialidades", ignore = true)
    Medicos fromRequest(MedicosRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "enderecoMedico", ignore = true)
    @Mapping(target = "estabelecimentos", ignore = true)
    @Mapping(target = "especialidades", ignore = true)
    void updateFromRequest(MedicosRequest request, @MappingTarget Medicos entity);

    MedicosResponse toResponse(Medicos entity);
}
