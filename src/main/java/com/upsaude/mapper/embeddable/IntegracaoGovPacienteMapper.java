package com.upsaude.mapper.embeddable;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.upsaude.api.response.embeddable.IntegracaoGovPacienteResponse;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.mapper.config.MappingConfig;

@Mapper(config = MappingConfig.class)
public interface IntegracaoGovPacienteMapper {
    IntegracaoGovPacienteMapper INSTANCE = Mappers.getMapper(IntegracaoGovPacienteMapper.class);

    default IntegracaoGovPacienteResponse toResponse(Paciente paciente) {
        if (paciente == null || paciente.getIntegracoesGov() == null || paciente.getIntegracoesGov().isEmpty()) {
            return null;
        }

        var integracao = paciente.getIntegracoesGov().get(0);

        return IntegracaoGovPacienteResponse.builder()
                .cartaoSusAtivo(integracao.getCartaoSusAtivo())
                .dataAtualizacaoCns(integracao.getDataAtualizacaoCns())
                .origemCadastro(integracao.getOrigemCadastro())
                .cnsValidado(integracao.getCnsValidado())
                .tipoCns(integracao.getTipoCns())
                .build();
    }
}

