package com.upsaude.mapper.profissional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.profissional.MedicosRequest;
import com.upsaude.api.response.estabelecimento.EstabelecimentosResponse;
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

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
public abstract class MedicosMapper {

    @Autowired
    protected EstabelecimentosMapper estabelecimentosMapper;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "enderecoMedico", ignore = true)
    @Mapping(target = "estabelecimentos", ignore = true)
    @Mapping(target = "especialidades", ignore = true)
    public abstract Medicos fromRequest(MedicosRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "enderecoMedico", ignore = true)
    @Mapping(target = "estabelecimentos", ignore = true)
    @Mapping(target = "especialidades", ignore = true)
    public abstract void updateFromRequest(MedicosRequest request, @MappingTarget Medicos entity);

    @Mapping(target = "estabelecimentos", expression = "java(mapMedicosEstabelecimentos(entity.getEstabelecimentos()))")
    public abstract MedicosResponse toResponse(Medicos entity);

    protected List<EstabelecimentosResponse> mapMedicosEstabelecimentos(List<MedicoEstabelecimento> estabelecimentos) {
        if (estabelecimentos == null) {
            return new ArrayList<>();
        }
        return estabelecimentos.stream()
                .map(MedicoEstabelecimento::getEstabelecimento)
                .filter(Objects::nonNull)
                .distinct() // Garante unicidade dos estabelecimentos
                .map(estabelecimentosMapper::toResponse)
                .collect(Collectors.toList());
    }
}
