package com.upsaude.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.upsaude.api.request.sistema.RelatorioEstatisticasRequest;
import com.upsaude.api.response.sistema.RelatorioEstatisticasResponse;
import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.repository.agendamento.AgendamentoRepository;
import com.upsaude.repository.clinica.atendimento.AtendimentoRepository;
import com.upsaude.repository.clinica.atendimento.ConsultasRepository;
import com.upsaude.repository.paciente.PacienteRepository;
import com.upsaude.service.sistema.RelatoriosService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RelatoriosServiceImpl implements RelatoriosService {

    private final AtendimentoRepository atendimentoRepository;
    private final ConsultasRepository consultasRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final PacienteRepository pacienteRepository;

    @Override
    public RelatorioEstatisticasResponse gerarEstatisticas(RelatorioEstatisticasRequest request) {
        log.debug("Gerando relatório de estatísticas. Data início: {}, Data fim: {}",
                request.getDataInicio(), request.getDataFim());

        LocalDate dataInicio = request.getDataInicio() != null ? request.getDataInicio() : LocalDate.now().minusMonths(1);
        LocalDate dataFim = request.getDataFim() != null ? request.getDataFim() : LocalDate.now();

        OffsetDateTime inicio = dataInicio.atStartOfDay().atOffset(ZoneOffset.UTC);
        OffsetDateTime fim = dataFim.atTime(23, 59, 59).atOffset(ZoneOffset.UTC);

        long totalAtendimentos = contarAtendimentos(request, inicio, fim);
        long totalConsultas = contarConsultas(request, inicio, fim);
        long totalAgendamentos = contarAgendamentos(request, inicio, fim);

        long totalPacientes = pacienteRepository.count();

        Map<String, Long> atendimentosPorTipo = new HashMap<>();
        Map<String, Long> atendimentosPorEspecialidade = new HashMap<>();
        Map<String, Long> examesPorTipo = new HashMap<>();
        Map<String, Long> procedimentosPorTipo = new HashMap<>();
        Map<String, Long> atendimentosPorProfissional = new HashMap<>();

        Map<String, BigDecimal> indicadoresSaude = calcularIndicadoresSaude(totalAgendamentos, totalConsultas, totalAtendimentos, inicio, fim);

        return RelatorioEstatisticasResponse.builder()
                .dataInicio(dataInicio)
                .dataFim(dataFim)
                .totalAtendimentos(totalAtendimentos)
                .totalConsultas(totalConsultas)
                .totalExames(0L)
                .totalProcedimentos(0L)
                .totalAgendamentos(totalAgendamentos)
                .totalPacientes(totalPacientes)
                .totalVisitasDomiciliares(0L)
                .atendimentosPorTipo(atendimentosPorTipo)
                .atendimentosPorEspecialidade(atendimentosPorEspecialidade)
                .examesPorTipo(examesPorTipo)
                .procedimentosPorTipo(procedimentosPorTipo)
                .atendimentosPorProfissional(atendimentosPorProfissional)
                .indicadoresSaude(indicadoresSaude)
                .build();
    }

    private long contarAtendimentos(RelatorioEstatisticasRequest request, OffsetDateTime inicio, OffsetDateTime fim) {

        return atendimentoRepository.findAll().stream()
                .filter(a -> a.getInformacoes() != null &&
                           a.getInformacoes().getDataHora() != null &&
                           !a.getInformacoes().getDataHora().isBefore(inicio) &&
                           !a.getInformacoes().getDataHora().isAfter(fim))
                .count();
    }

    private long contarConsultas(RelatorioEstatisticasRequest request, OffsetDateTime inicio, OffsetDateTime fim) {

        return consultasRepository.findAll().stream()
                .filter(c -> {
                    if (c.getInformacoes() != null && c.getInformacoes().getDataConsulta() != null) {
                        OffsetDateTime dataConsulta = c.getInformacoes().getDataConsulta();
                        return !dataConsulta.isBefore(inicio) && !dataConsulta.isAfter(fim);
                    }

                    return c.getCreatedAt() != null &&
                           !c.getCreatedAt().isBefore(inicio) &&
                           !c.getCreatedAt().isAfter(fim);
                })
                .count();
    }


    private long contarAgendamentos(RelatorioEstatisticasRequest request, OffsetDateTime inicio, OffsetDateTime fim) {

        List<Agendamento> agendamentos = agendamentoRepository.findByDataHoraBetweenOrderByDataHoraAsc(
                inicio, fim, PageRequest.of(0, Integer.MAX_VALUE)).getContent();
        return agendamentos.size();
    }

    private Map<String, BigDecimal> calcularIndicadoresSaude(long totalAgendamentos,
                                                             long agendamentosRealizados,
                                                             long totalAtendimentos,
                                                             OffsetDateTime inicio,
                                                             OffsetDateTime fim) {
        Map<String, BigDecimal> indicadores = new HashMap<>();

        if (totalAgendamentos > 0) {
            BigDecimal taxaOcupacao = BigDecimal.valueOf(agendamentosRealizados)
                    .divide(BigDecimal.valueOf(totalAgendamentos), 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
            indicadores.put("taxaOcupacaoAgendamentos", taxaOcupacao);
        }

        long dias = java.time.temporal.ChronoUnit.DAYS.between(inicio.toLocalDate(), fim.toLocalDate()) + 1;
        if (dias > 0) {
            BigDecimal mediaAtendimentosDia = BigDecimal.valueOf(totalAtendimentos)
                    .divide(BigDecimal.valueOf(dias), 2, RoundingMode.HALF_UP);
            indicadores.put("mediaAtendimentosPorDia", mediaAtendimentosDia);
        }

        return indicadores;
    }
}
