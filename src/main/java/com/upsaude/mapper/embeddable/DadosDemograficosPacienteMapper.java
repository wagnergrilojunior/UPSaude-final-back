package com.upsaude.mapper.embeddable;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.upsaude.api.response.embeddable.DadosDemograficosPacienteResponse;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.mapper.config.MappingConfig;
import com.upsaude.mapper.paciente.DadosSociodemograficosMapper;
import com.upsaude.mapper.paciente.PacienteDadosPessoaisComplementaresMapper;

@Mapper(config = MappingConfig.class, uses = {DadosSociodemograficosMapper.class, PacienteDadosPessoaisComplementaresMapper.class})
public interface DadosDemograficosPacienteMapper {
    DadosDemograficosPacienteMapper INSTANCE = Mappers.getMapper(DadosDemograficosPacienteMapper.class);

    default DadosDemograficosPacienteResponse toResponse(Paciente paciente) {
        if (paciente == null) {
            return null;
        }

        DadosDemograficosPacienteResponse.DadosDemograficosPacienteResponseBuilder builder = DadosDemograficosPacienteResponse.builder();

        if (paciente.getDadosSociodemograficos() != null) {
            builder.estadoCivil(null)
                    .escolaridade(paciente.getDadosSociodemograficos().getEscolaridade())
                    .nacionalidade(paciente.getDadosSociodemograficos().getNacionalidade())
                    .naturalidade(paciente.getDadosSociodemograficos().getNaturalidade())
                    .paisNascimento(paciente.getDadosSociodemograficos().getPaisNascimento())
                    .municipioNascimentoIbge(paciente.getDadosSociodemograficos().getMunicipioNascimentoIbge())
                    .racaCor(paciente.getDadosSociodemograficos().getRacaCor())
                    .ocupacaoProfissao(paciente.getDadosSociodemograficos().getOcupacaoProfissao())
                    .situacaoRua(paciente.getDadosSociodemograficos().getSituacaoRua());
        }

        if (paciente.getDadosPessoaisComplementares() != null) {
            builder.identidadeGenero(paciente.getDadosPessoaisComplementares().getIdentidadeGenero())
                    .orientacaoSexual(paciente.getDadosPessoaisComplementares().getOrientacaoSexual());
        }

        return builder.build();
    }
}

