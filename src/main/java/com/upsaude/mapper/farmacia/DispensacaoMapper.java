package com.upsaude.mapper.farmacia;

import com.upsaude.api.request.farmacia.DispensacaoRequest;
import com.upsaude.api.response.farmacia.DispensacaoResponse;
import com.upsaude.entity.farmacia.Dispensacao;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {DispensacaoItemMapper.class})
public interface DispensacaoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "farmacia", ignore = true)
    @Mapping(target = "receita", ignore = true)
    @Mapping(target = "profissionalSaude", ignore = true)
    @Mapping(target = "itens", ignore = true)
    Dispensacao fromRequest(DispensacaoRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "farmacia", ignore = true)
    @Mapping(target = "receita", ignore = true)
    @Mapping(target = "profissionalSaude", ignore = true)
    @Mapping(target = "itens", ignore = true)
    void updateFromRequest(DispensacaoRequest request, @MappingTarget Dispensacao entity);

    @Mapping(target = "pacienteId", source = "paciente.id")
    @Mapping(target = "pacienteNome", ignore = true)
    @Mapping(target = "farmaciaId", source = "farmacia.id")
    @Mapping(target = "farmaciaNome", ignore = true)
    @Mapping(target = "receitaId", source = "receita.id")
    @Mapping(target = "numeroReceita", source = "receita.numeroReceita")
    @Mapping(target = "profissionalSaudeId", source = "profissionalSaude.id")
    @Mapping(target = "profissionalNome", ignore = true)
    @Mapping(target = "itens", ignore = true)
    DispensacaoResponse toResponse(Dispensacao dispensacao);

    default DispensacaoResponse toResponseCompleto(Dispensacao dispensacao) {
        if (dispensacao == null) return null;
        
        DispensacaoResponse response = toResponse(dispensacao);
        
        if (dispensacao.getPaciente() != null) {
            response.setPacienteNome(dispensacao.getPaciente().getNomeCompleto());
        }
        
        if (dispensacao.getFarmacia() != null) {
            response.setFarmaciaNome(dispensacao.getFarmacia().getNome());
        }
        
        if (dispensacao.getProfissionalSaude() != null && 
            dispensacao.getProfissionalSaude().getDadosPessoaisBasicos() != null) {
            response.setProfissionalNome(dispensacao.getProfissionalSaude().getDadosPessoaisBasicos().getNomeCompleto());
        }
        
        if (dispensacao.getItens() != null && !dispensacao.getItens().isEmpty()) {
            DispensacaoItemMapper itemMapper = org.mapstruct.factory.Mappers.getMapper(DispensacaoItemMapper.class);
            response.setItens(dispensacao.getItens().stream()
                    .map(itemMapper::toResponseCompleto)
                    .toList());
        }
        
        return response;
    }
}

