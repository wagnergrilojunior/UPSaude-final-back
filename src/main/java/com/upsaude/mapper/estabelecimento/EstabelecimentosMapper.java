package com.upsaude.mapper.estabelecimento;

import com.upsaude.api.request.estabelecimento.EstabelecimentosRequest;
import com.upsaude.api.response.estabelecimento.EstabelecimentosResponse;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.embeddable.ContatoEstabelecimentoMapper;
import com.upsaude.mapper.embeddable.DadosIdentificacaoEstabelecimentoMapper;
import com.upsaude.mapper.embeddable.InfraestruturaFisicaEstabelecimentoMapper;
import com.upsaude.mapper.embeddable.LicenciamentoEstabelecimentoMapper;
import com.upsaude.mapper.embeddable.LocalizacaoEstabelecimentoMapper;
import com.upsaude.mapper.embeddable.ResponsaveisEstabelecimentoMapper;
import com.upsaude.mapper.geral.EnderecoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {
    DadosIdentificacaoEstabelecimentoMapper.class,
    ContatoEstabelecimentoMapper.class,
    ResponsaveisEstabelecimentoMapper.class,
    LicenciamentoEstabelecimentoMapper.class,
    InfraestruturaFisicaEstabelecimentoMapper.class,
    LocalizacaoEstabelecimentoMapper.class,
    EnderecoMapper.class
})
public interface EstabelecimentosMapper {

    @Mapping(target = "active", ignore = true)
    @Mapping(target = "equipamentos", ignore = true)
    @Mapping(target = "endereco", ignore = true)
    @Mapping(target = "responsaveis.responsavelTecnico", ignore = true)
    @Mapping(target = "responsaveis.responsavelAdministrativo", ignore = true)
    Estabelecimentos toEntity(EstabelecimentosResponse dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "endereco", ignore = true)
    @Mapping(target = "equipamentos", ignore = true)
    Estabelecimentos fromRequest(EstabelecimentosRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "endereco", ignore = true)
    @Mapping(target = "equipamentos", ignore = true)
    void updateFromRequest(EstabelecimentosRequest request, @MappingTarget Estabelecimentos entity);

    @Mapping(target = "equipamentos", ignore = true)
    EstabelecimentosResponse toResponse(Estabelecimentos entity);
}
