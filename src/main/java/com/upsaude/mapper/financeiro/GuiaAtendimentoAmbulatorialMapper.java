package com.upsaude.mapper.financeiro;

import com.upsaude.api.request.financeiro.GuiaAtendimentoAmbulatorialRequest;
import com.upsaude.api.response.agendamento.AgendamentoSimplificadoResponse;
import com.upsaude.api.response.agendamento.EstabelecimentoSimplificadoResponse;
import com.upsaude.api.response.clinica.atendimento.AtendimentoSimplificadoResponse;
import com.upsaude.api.response.faturamento.DocumentoFaturamentoSimplificadoResponse;
import com.upsaude.api.response.financeiro.GuiaAtendimentoAmbulatorialResponse;
import com.upsaude.api.response.financeiro.GuiaAtendimentoAmbulatorialSimplificadoResponse;
import com.upsaude.api.response.sistema.usuario.PacienteSimplificadoResponse;
import com.upsaude.api.response.sistema.usuario.UsuarioSistemaSimplificadoResponse;
import com.upsaude.entity.financeiro.GuiaAtendimentoAmbulatorial;
import com.upsaude.mapper.config.MappingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MappingConfig.class, uses = { CompetenciaFinanceiraMapper.class, UsuarioSistemaMapper.class })
public interface GuiaAtendimentoAmbulatorialMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "competencia", ignore = true)
    @Mapping(target = "agendamento", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "documentoFaturamento", ignore = true)
    @Mapping(target = "emitidaEm", ignore = true)
    @Mapping(target = "canceladaEm", ignore = true)
    @Mapping(target = "canceladaPor", ignore = true)
    GuiaAtendimentoAmbulatorial fromRequest(GuiaAtendimentoAmbulatorialRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "competencia", ignore = true)
    @Mapping(target = "agendamento", ignore = true)
    @Mapping(target = "atendimento", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "estabelecimento", ignore = true)
    @Mapping(target = "documentoFaturamento", ignore = true)
    @Mapping(target = "emitidaEm", ignore = true)
    @Mapping(target = "canceladaEm", ignore = true)
    @Mapping(target = "canceladaPor", ignore = true)
    void updateFromRequest(GuiaAtendimentoAmbulatorialRequest request, @MappingTarget GuiaAtendimentoAmbulatorial entity);

    @Mapping(target = "canceladaPor", source = "canceladaPor", qualifiedByName = "mapUsuarioSistemaSimplificado")
    @Mapping(target = "documentoFaturamento", source = "documentoFaturamento", qualifiedByName = "mapDocumentoFaturamentoParaGuia")
    GuiaAtendimentoAmbulatorialResponse toResponse(GuiaAtendimentoAmbulatorial entity);

    @Named("toSimplifiedResponse")
    GuiaAtendimentoAmbulatorialSimplificadoResponse toSimplifiedResponse(GuiaAtendimentoAmbulatorial entity);

    default AgendamentoSimplificadoResponse mapAgendamento(com.upsaude.entity.agendamento.Agendamento agendamento) {
        if (agendamento == null) return null;
        try {
            return AgendamentoSimplificadoResponse.builder()
                    .id(agendamento.getId())
                    .dataHora(agendamento.getDataHora())
                    .dataHoraFim(agendamento.getDataHoraFim())
                    .status(agendamento.getStatus())
                    .build();
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return null;
        }
    }

    default AtendimentoSimplificadoResponse mapAtendimento(com.upsaude.entity.clinica.atendimento.Atendimento atendimento) {
        if (atendimento == null) return null;
        try {
            return AtendimentoSimplificadoResponse.builder()
                    .id(atendimento.getId())
                    .dataHora(atendimento.getInformacoes() != null ? atendimento.getInformacoes().getDataHora() : null)
                    .statusAtendimento(atendimento.getInformacoes() != null ? atendimento.getInformacoes().getStatusAtendimento() : null)
                    .build();
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return null;
        }
    }

    default PacienteSimplificadoResponse mapPaciente(com.upsaude.entity.paciente.Paciente paciente) {
        if (paciente == null) return null;
        try {
            return PacienteSimplificadoResponse.builder()
                    .id(paciente.getId())
                    .nome(paciente.getNomeCompleto())
                    .email(null)
                    .build();
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return null;
        }
    }

    default EstabelecimentoSimplificadoResponse mapEstabelecimento(com.upsaude.entity.estabelecimento.Estabelecimentos est) {
        if (est == null) return null;
        try {
            String nome = est.getDadosIdentificacao() != null ? est.getDadosIdentificacao().getNome() : null;
            String nomeFantasia = est.getDadosIdentificacao() != null ? est.getDadosIdentificacao().getNomeFantasia() : null;
            return EstabelecimentoSimplificadoResponse.builder()
                    .id(est.getId())
                    .razaoSocial(nome)
                    .nomeFantasia(nomeFantasia)
                    .build();
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return null;
        }
    }

    @Named("mapDocumentoFaturamentoParaGuia")
    default DocumentoFaturamentoSimplificadoResponse mapDocumentoFaturamentoParaGuia(com.upsaude.entity.faturamento.DocumentoFaturamento doc) {
        if (doc == null) return null;
        try {
            return DocumentoFaturamentoSimplificadoResponse.builder()
                    .id(doc.getId())
                    .tipo(doc.getTipo())
                    .numero(doc.getNumero())
                    .serie(doc.getSerie())
                    .status(doc.getStatus())
                    .build();
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return null;
        }
    }

}

