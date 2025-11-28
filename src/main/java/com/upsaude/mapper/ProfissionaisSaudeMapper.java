package com.upsaude.mapper;

import com.upsaude.api.request.ProfissionaisSaudeRequest;
import com.upsaude.api.response.ProfissionaisSaudeResponse;
import com.upsaude.dto.ProfissionaisSaudeDTO;
import com.upsaude.entity.ConselhosProfissionais;
import com.upsaude.entity.EspecialidadesMedicas;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(config = MappingConfig.class)
public interface ProfissionaisSaudeMapper extends EntityMapper<ProfissionaisSaude, ProfissionaisSaudeDTO> {

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "conselho", source = "conselhoId", qualifiedByName = "conselhoFromId")
    @Mapping(target = "especialidades", source = "especialidadesIds", qualifiedByName = "especialidadesFromIds")
    @Mapping(target = "enderecoProfissional", source = "enderecoProfissionalId", qualifiedByName = "enderecoFromId")
    @Mapping(target = "vinculosEstabelecimentos", ignore = true)
    @Mapping(target = "vinculosEquipes", ignore = true)
    ProfissionaisSaude toEntity(ProfissionaisSaudeDTO dto);

    @Mapping(target = "conselhoId", source = "conselho.id")
    @Mapping(target = "especialidadesIds", source = "especialidades", qualifiedByName = "idsFromEspecialidades")
    @Mapping(target = "enderecoProfissionalId", source = "enderecoProfissional.id")
    @Mapping(target = "estabelecimentosIds", source = "vinculosEstabelecimentos", qualifiedByName = "idsFromVinculosEstabelecimentos")
    ProfissionaisSaudeDTO toDTO(ProfissionaisSaude entity);

    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "conselho", source = "conselhoId", qualifiedByName = "conselhoFromId")
    @Mapping(target = "especialidades", source = "especialidadesIds", qualifiedByName = "especialidadesFromIds")
    @Mapping(target = "enderecoProfissional", source = "enderecoProfissionalId", qualifiedByName = "enderecoFromId")
    @Mapping(target = "vinculosEstabelecimentos", ignore = true)
    @Mapping(target = "vinculosEquipes", ignore = true)
    ProfissionaisSaude fromRequest(ProfissionaisSaudeRequest request);

    @Mapping(target = "conselhoId", source = "conselho.id")
    @Mapping(target = "conselhoSigla", source = "conselho.sigla")
    @Mapping(target = "conselhoNome", source = "conselho.nome")
    @Mapping(target = "especialidadesIds", source = "especialidades", qualifiedByName = "idsFromEspecialidades")
    @Mapping(target = "especialidadesNomes", source = "especialidades", qualifiedByName = "nomesFromEspecialidades")
    @Mapping(target = "enderecoProfissionalId", source = "enderecoProfissional.id")
    @Mapping(target = "estabelecimentosIds", source = "vinculosEstabelecimentos", qualifiedByName = "idsFromVinculosEstabelecimentos")
    @Mapping(target = "estabelecimentosNomes", source = "vinculosEstabelecimentos", qualifiedByName = "nomesFromVinculosEstabelecimentos")
    ProfissionaisSaudeResponse toResponse(ProfissionaisSaude entity);

    @Named("conselhoFromId")
    default ConselhosProfissionais conselhoFromId(UUID id) {
        if (id == null) return null;
        ConselhosProfissionais c = new ConselhosProfissionais();
        c.setId(id);
        return c;
    }

    @Named("especialidadesFromIds")
    default List<EspecialidadesMedicas> especialidadesFromIds(List<UUID> ids) {
        if (ids == null) return new ArrayList<>();
        List<EspecialidadesMedicas> list = new ArrayList<>();
        for (UUID id : ids) {
            EspecialidadesMedicas e = new EspecialidadesMedicas();
            e.setId(id);
            list.add(e);
        }
        return list;
    }

    @Named("idsFromEspecialidades")
    default List<UUID> idsFromEspecialidades(List<EspecialidadesMedicas> especialidades) {
        if (especialidades == null) return new ArrayList<>();
        return especialidades.stream()
                .filter(e -> e != null && e.getId() != null)
                .map(EspecialidadesMedicas::getId)
                .collect(Collectors.toList());
    }

    @Named("nomesFromEspecialidades")
    default List<String> nomesFromEspecialidades(List<EspecialidadesMedicas> especialidades) {
        if (especialidades == null) return new ArrayList<>();
        return especialidades.stream()
                .filter(e -> e != null && e.getNome() != null)
                .map(EspecialidadesMedicas::getNome)
                .collect(Collectors.toList());
    }

    @Named("enderecoFromId")
    default com.upsaude.entity.Endereco enderecoFromId(UUID id) {
        if (id == null) return null;
        com.upsaude.entity.Endereco e = new com.upsaude.entity.Endereco();
        e.setId(id);
        return e;
    }

    @Named("idsFromVinculosEstabelecimentos")
    default List<UUID> idsFromVinculosEstabelecimentos(Set<com.upsaude.entity.ProfissionalEstabelecimento> vinculos) {
        if (vinculos == null) return new ArrayList<>();
        return vinculos.stream()
                .filter(v -> v != null && v.getEstabelecimento() != null && v.getEstabelecimento().getId() != null)
                .map(v -> v.getEstabelecimento().getId())
                .distinct()
                .collect(Collectors.toList());
    }

    @Named("nomesFromVinculosEstabelecimentos")
    default List<String> nomesFromVinculosEstabelecimentos(Set<com.upsaude.entity.ProfissionalEstabelecimento> vinculos) {
        if (vinculos == null) return new ArrayList<>();
        return vinculos.stream()
                .filter(v -> v != null && v.getEstabelecimento() != null && v.getEstabelecimento().getNome() != null)
                .map(v -> v.getEstabelecimento().getNome())
                .distinct()
                .collect(Collectors.toList());
    }
}

