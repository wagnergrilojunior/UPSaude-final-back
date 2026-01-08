package com.upsaude.mapper.profissional;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.api.request.profissional.ProfissionaisSaudeRequest;
import com.upsaude.api.response.profissional.ProfissionaisSaudeResponse;
import com.upsaude.api.response.profissional.SigtapOcupacaoSimplificadaResponse;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.embeddable.RegistroProfissional;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.embeddable.ContatoProfissionalMapper;
import com.upsaude.mapper.embeddable.DadosDeficienciaProfissionalMapper;
import com.upsaude.mapper.embeddable.DadosDemograficosProfissionalMapper;
import com.upsaude.mapper.embeddable.DadosPessoaisBasicosProfissionalMapper;
import com.upsaude.mapper.embeddable.DocumentosBasicosProfissionalMapper;
import com.upsaude.mapper.embeddable.RegistroProfissionalMapper;
import com.upsaude.mapper.geral.EnderecoMapper;

@Mapper(config = MappingConfig.class, uses = {
    EnderecoMapper.class,
    DadosPessoaisBasicosProfissionalMapper.class,
    DocumentosBasicosProfissionalMapper.class,
    DadosDemograficosProfissionalMapper.class,
    DadosDeficienciaProfissionalMapper.class,
    RegistroProfissionalMapper.class,
    ContatoProfissionalMapper.class
})
public interface ProfissionaisSaudeMapper  {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "enderecoProfissional", ignore = true)
    @Mapping(target = "sigtapOcupacao", ignore = true)
    ProfissionaisSaude fromRequest(ProfissionaisSaudeRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "enderecoProfissional", ignore = true)
    @Mapping(target = "sigtapOcupacao", ignore = true)
    @Mapping(target = "registroProfissional", ignore = true)
    void updateFromRequest(ProfissionaisSaudeRequest request, @MappingTarget ProfissionaisSaude entity);

    @AfterMapping
    default void updateRegistroProfissionalAfterMapping(ProfissionaisSaudeRequest request, @MappingTarget ProfissionaisSaude entity) {
        if (request != null && request.getRegistroProfissional() != null) {
            if (entity.getRegistroProfissional() == null) {
                entity.setRegistroProfissional(new RegistroProfissional());
            }
            com.upsaude.mapper.embeddable.RegistroProfissionalMapper mapper = org.mapstruct.factory.Mappers.getMapper(com.upsaude.mapper.embeddable.RegistroProfissionalMapper.class);
            mapper.updateFromRequest(request.getRegistroProfissional(), entity.getRegistroProfissional());
        }
    }

    @Mapping(target = "sigtapOcupacao", source = "sigtapOcupacao", qualifiedByName = "mapSigtapOcupacaoSimplificada")
    ProfissionaisSaudeResponse toResponse(ProfissionaisSaude entity);

    @org.mapstruct.Named("mapSigtapOcupacaoSimplificada")
    default SigtapOcupacaoSimplificadaResponse mapSigtapOcupacaoSimplificada(com.upsaude.entity.referencia.sigtap.SigtapOcupacao sigtapOcupacao) {
        if (sigtapOcupacao == null) {
            return null;
        }
        try {
            return SigtapOcupacaoSimplificadaResponse.builder()
                    .id(sigtapOcupacao.getId())
                    .nome(sigtapOcupacao.getNome())
                    .build();
        } catch (Exception e) {
            System.err.println("Erro ao mapear sigtapOcupacao: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
