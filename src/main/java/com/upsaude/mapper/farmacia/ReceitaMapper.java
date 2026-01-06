package com.upsaude.mapper.farmacia;

import com.upsaude.api.request.farmacia.ReceitaRequest;
import com.upsaude.api.response.farmacia.ReceitaResponse;
import com.upsaude.entity.farmacia.Receita;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MappingConfig.class, uses = {ReceitaItemMapper.class})
public interface ReceitaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "consulta", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "itens", ignore = true)
    Receita fromRequest(ReceitaRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "consulta", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "itens", ignore = true)
    void updateFromRequest(ReceitaRequest request, @MappingTarget Receita entity);

    @Mapping(target = "pacienteId", source = "paciente.id")
    @Mapping(target = "pacienteNome", ignore = true)
    @Mapping(target = "consultaId", source = "consulta.id")
    @Mapping(target = "medicoId", source = "medico.id")
    @Mapping(target = "medicoNome", ignore = true)
    @Mapping(target = "itens", ignore = true)
    ReceitaResponse toResponse(Receita receita);

    default ReceitaResponse toResponseCompleto(Receita receita) {
        if (receita == null) return null;

        ReceitaResponse response = toResponse(receita);

        if (receita.getPaciente() != null) {
            response.setPacienteNome(receita.getPaciente().getNomeCompleto());
        }

        if (receita.getMedico() != null && receita.getMedico().getDadosPessoaisBasicos() != null) {
            response.setMedicoNome(receita.getMedico().getDadosPessoaisBasicos().getNomeCompleto());
        }

        if (receita.getItens() != null && !receita.getItens().isEmpty()) {
            ReceitaItemMapper itemMapper = org.mapstruct.factory.Mappers.getMapper(ReceitaItemMapper.class);
            response.setItens(receita.getItens().stream()
                    .map(itemMapper::toResponseCompleto)
                    .toList());
        }

        return response;
    }
}
