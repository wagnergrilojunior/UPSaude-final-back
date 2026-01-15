package com.upsaude.service.impl.api.sistema.relatorios;

import com.upsaude.api.request.sistema.relatorios.RelatorioComparativoRequest;
import com.upsaude.api.response.sistema.relatorios.RelatorioComparativoResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.agendamento.AgendamentoRepository;
import com.upsaude.repository.clinica.atendimento.AtendimentoRepository;
import com.upsaude.repository.clinica.atendimento.ConsultasRepository;
import com.upsaude.service.api.sistema.relatorios.RelatorioComparativoService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.relatorios.EstabelecimentoFilterHelper;
import com.upsaude.service.api.support.relatorios.MedicoFilterHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RelatorioComparativoServiceImpl implements RelatorioComparativoService {

    private final TenantService tenantService;
    private final AtendimentoRepository atendimentoRepository;
    private final ConsultasRepository consultasRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final EstabelecimentoFilterHelper estabelecimentoFilterHelper;
    private final MedicoFilterHelper medicoFilterHelper;

    @Override
    @org.springframework.cache.annotation.Cacheable(
            value = "relatorioComparativo",
            keyGenerator = "relatoriosCacheKeyGenerator",
            unless = "#result == null"
    )
    public RelatorioComparativoResponse gerarRelatorioComparativo(RelatorioComparativoRequest request) {
        log.debug("Gerando relatório comparativo. Período atual: {} a {}, Período anterior: {} a {}",
                request.getPeriodoAtualInicio(), request.getPeriodoAtualFim(),
                request.getPeriodoAnteriorInicio(), request.getPeriodoAnteriorFim());

        UUID tenantId = tenantService.validarTenantAtual();

        // Validar e calcular períodos
        LocalDate periodoAtualInicio = request.getPeriodoAtualInicio();
        LocalDate periodoAtualFim = request.getPeriodoAtualFim();
        LocalDate periodoAnteriorInicio = request.getPeriodoAnteriorInicio();
        LocalDate periodoAnteriorFim = request.getPeriodoAnteriorFim();

        if (periodoAtualInicio == null || periodoAtualFim == null) {
            throw new BadRequestException("Período atual (início e fim) é obrigatório");
        }

        if (periodoAtualInicio.isAfter(periodoAtualFim)) {
            throw new BadRequestException("Data de início do período atual deve ser anterior à data de fim");
        }

        // Calcular período anterior se não fornecido
        if (periodoAnteriorInicio == null || periodoAnteriorFim == null) {
            long diasPeriodo = java.time.temporal.ChronoUnit.DAYS.between(periodoAtualInicio, periodoAtualFim) + 1;
            periodoAnteriorFim = periodoAtualInicio.minusDays(1);
            periodoAnteriorInicio = periodoAnteriorFim.minusDays(diasPeriodo - 1);
        }

        if (periodoAnteriorInicio.isAfter(periodoAnteriorFim)) {
            throw new BadRequestException("Data de início do período anterior deve ser anterior à data de fim");
        }

        // Validar filtros granulares
        if (request.getEstabelecimentoId() != null) {
            if (!estabelecimentoFilterHelper.validarEstabelecimentoPertenceAoTenant(request.getEstabelecimentoId(), tenantId)) {
                throw new BadRequestException("Estabelecimento não encontrado ou não pertence ao tenant");
            }
        }

        if (request.getMedicoId() != null) {
            if (!medicoFilterHelper.validarMedicoPertenceAoTenant(request.getMedicoId(), tenantId)) {
                throw new BadRequestException("Médico não encontrado ou não pertence ao tenant");
            }
        }

        // Coletar dados do período atual
        RelatorioComparativoResponse.DadosPeriodo dadosAtual = coletarDadosPeriodo(
                tenantId,
                periodoAtualInicio,
                periodoAtualFim,
                request.getEstabelecimentoId(),
                request.getMedicoId(),
                request.getEspecialidadeId()
        );

        // Coletar dados do período anterior
        RelatorioComparativoResponse.DadosPeriodo dadosAnterior = coletarDadosPeriodo(
                tenantId,
                periodoAnteriorInicio,
                periodoAnteriorFim,
                request.getEstabelecimentoId(),
                request.getMedicoId(),
                request.getEspecialidadeId()
        );

        // Calcular comparações
        RelatorioComparativoResponse.Comparacoes comparacoes = calcularComparacoes(dadosAtual, dadosAnterior);

        // Determinar tipo de comparação
        RelatorioComparativoRequest.TipoComparacao tipoComparacao = request.getTipoComparacao();
        if (tipoComparacao == null) {
            if (request.getEstabelecimentoId() != null) {
                tipoComparacao = RelatorioComparativoRequest.TipoComparacao.ESTABELECIMENTO;
            } else if (request.getMedicoId() != null) {
                tipoComparacao = RelatorioComparativoRequest.TipoComparacao.MEDICO;
            } else if (request.getEspecialidadeId() != null) {
                tipoComparacao = RelatorioComparativoRequest.TipoComparacao.ESPECIALIDADE;
            } else {
                tipoComparacao = RelatorioComparativoRequest.TipoComparacao.PERIODO_TEMPORAL;
            }
        }

        return RelatorioComparativoResponse.builder()
                .tipoComparacao(convertTipoComparacao(tipoComparacao))
                .periodoAtualInicio(periodoAtualInicio)
                .periodoAtualFim(periodoAtualFim)
                .periodoAnteriorInicio(periodoAnteriorInicio)
                .periodoAnteriorFim(periodoAnteriorFim)
                .periodoAtual(dadosAtual)
                .periodoAnterior(dadosAnterior)
                .comparacoes(comparacoes)
                .estabelecimentoId(request.getEstabelecimentoId())
                .medicoId(request.getMedicoId())
                .especialidadeId(request.getEspecialidadeId())
                .build();
    }

    private RelatorioComparativoResponse.DadosPeriodo coletarDadosPeriodo(
            UUID tenantId,
            LocalDate dataInicio,
            LocalDate dataFim,
            UUID estabelecimentoId,
            UUID medicoId,
            UUID especialidadeId) {

        OffsetDateTime inicio = dataInicio.atStartOfDay().atOffset(ZoneOffset.UTC);
        OffsetDateTime fim = dataFim.atTime(23, 59, 59).atOffset(ZoneOffset.UTC);

        // Contar atendimentos
        long totalAtendimentos = contarAtendimentos(tenantId, inicio, fim, estabelecimentoId, medicoId);
        long totalConsultas = contarConsultas(tenantId, inicio, fim, estabelecimentoId, medicoId);
        long totalAgendamentos = contarAgendamentos(tenantId, inicio, fim, estabelecimentoId, medicoId, especialidadeId);
        long totalPacientes = contarPacientes(tenantId, estabelecimentoId, medicoId);
        long totalProcedimentos = contarProcedimentos(tenantId, inicio, fim, estabelecimentoId, medicoId);

        // Calcular indicadores
        Map<String, BigDecimal> indicadores = calcularIndicadores(
                totalAtendimentos, totalConsultas, totalAgendamentos, totalPacientes, inicio, fim);

        // Agregar por tipo e especialidade
        Map<String, Long> atendimentosPorTipo = calcularAtendimentosPorTipo(
                tenantId, inicio, fim, estabelecimentoId, medicoId);
        Map<String, Long> atendimentosPorEspecialidade = calcularAtendimentosPorEspecialidade(
                tenantId, inicio, fim, estabelecimentoId, medicoId, especialidadeId);

        return RelatorioComparativoResponse.DadosPeriodo.builder()
                .totalAtendimentos(totalAtendimentos)
                .totalConsultas(totalConsultas)
                .totalAgendamentos(totalAgendamentos)
                .totalPacientes(totalPacientes)
                .totalProcedimentos(totalProcedimentos)
                .valorTotal(BigDecimal.ZERO) // TODO: Calcular valor total quando houver dados financeiros
                .atendimentosPorTipo(atendimentosPorTipo)
                .atendimentosPorEspecialidade(atendimentosPorEspecialidade)
                .indicadores(indicadores)
                .build();
    }

    private long contarAtendimentos(UUID tenantId, OffsetDateTime inicio, OffsetDateTime fim, UUID estabelecimentoId, UUID medicoId) {
        return atendimentoRepository.findAllByTenant(tenantId, PageRequest.of(0, Integer.MAX_VALUE))
                .getContent().stream()
                .filter(a -> {
                    if (a.getInformacoes() == null || a.getInformacoes().getDataHora() == null) {
                        return false;
                    }
                    OffsetDateTime dataHora = a.getInformacoes().getDataHora();
                    return !dataHora.isBefore(inicio) && !dataHora.isAfter(fim);
                })
                .filter(a -> estabelecimentoId == null || (a.getEstabelecimento() != null && estabelecimentoId.equals(a.getEstabelecimento().getId())))
                .filter(a -> medicoId == null || (a.getProfissional() != null && medicoId.equals(a.getProfissional().getId())))
                .count();
    }

    private long contarConsultas(UUID tenantId, OffsetDateTime inicio, OffsetDateTime fim, UUID estabelecimentoId, UUID medicoId) {
        return consultasRepository.findAllByTenant(tenantId, PageRequest.of(0, Integer.MAX_VALUE))
                .getContent().stream()
                .filter(c -> {
                    OffsetDateTime dataConsulta = c.getInformacoes() != null && c.getInformacoes().getDataConsulta() != null
                            ? c.getInformacoes().getDataConsulta()
                            : c.getCreatedAt();
                    return dataConsulta != null && !dataConsulta.isBefore(inicio) && !dataConsulta.isAfter(fim);
                })
                .filter(c -> estabelecimentoId == null || (c.getAtendimento() != null && c.getAtendimento().getEstabelecimento() != null
                        && estabelecimentoId.equals(c.getAtendimento().getEstabelecimento().getId())))
                .filter(c -> medicoId == null || (c.getAtendimento() != null && c.getAtendimento().getProfissional() != null
                        && medicoId.equals(c.getAtendimento().getProfissional().getId())))
                .count();
    }

    private long contarAgendamentos(UUID tenantId, OffsetDateTime inicio, OffsetDateTime fim, UUID estabelecimentoId, UUID medicoId, UUID especialidadeId) {
        if (estabelecimentoId != null && medicoId != null) {
            return agendamentoRepository.findByEstabelecimentoIdAndMedicoIdAndDataHoraBetweenAndTenantIdOrderByDataHoraAsc(
                    estabelecimentoId, medicoId, inicio, fim, tenantId, PageRequest.of(0, Integer.MAX_VALUE)).getTotalElements();
        } else if (estabelecimentoId != null) {
            return agendamentoRepository.findByEstabelecimentoIdAndDataHoraBetweenAndTenantIdOrderByDataHoraAsc(
                    estabelecimentoId, inicio, fim, tenantId, PageRequest.of(0, Integer.MAX_VALUE)).getTotalElements();
        } else if (medicoId != null) {
            return agendamentoRepository.findByMedicoIdAndDataHoraBetweenAndTenantIdOrderByDataHoraAsc(
                    medicoId, inicio, fim, tenantId, PageRequest.of(0, Integer.MAX_VALUE)).getTotalElements();
        } else if (especialidadeId != null) {
            return agendamentoRepository.findByEspecialidadeIdAndDataHoraBetweenAndTenantIdOrderByDataHoraAsc(
                    especialidadeId, inicio, fim, tenantId, PageRequest.of(0, Integer.MAX_VALUE)).getTotalElements();
        } else {
            return agendamentoRepository.findByDataHoraBetweenAndTenantIdOrderByDataHoraAsc(
                    inicio, fim, tenantId, PageRequest.of(0, Integer.MAX_VALUE)).getTotalElements();
        }
    }

    private long contarPacientes(UUID tenantId, UUID estabelecimentoId, UUID medicoId) {
        // Contar pacientes únicos baseado em atendimentos
        return atendimentoRepository.findAllByTenant(tenantId, PageRequest.of(0, Integer.MAX_VALUE))
                .getContent().stream()
                .filter(a -> estabelecimentoId == null || (a.getEstabelecimento() != null && estabelecimentoId.equals(a.getEstabelecimento().getId())))
                .filter(a -> medicoId == null || (a.getProfissional() != null && medicoId.equals(a.getProfissional().getId())))
                .map(a -> a.getPaciente().getId())
                .distinct()
                .count();
    }

    private long contarProcedimentos(UUID tenantId, OffsetDateTime inicio, OffsetDateTime fim, UUID estabelecimentoId, UUID medicoId) {
        // Contar procedimentos realizados (via atendimentos)
        return atendimentoRepository.findAllByTenant(tenantId, PageRequest.of(0, Integer.MAX_VALUE))
                .getContent().stream()
                .filter(a -> {
                    if (a.getInformacoes() == null || a.getInformacoes().getDataHora() == null) {
                        return false;
                    }
                    OffsetDateTime dataHora = a.getInformacoes().getDataHora();
                    return !dataHora.isBefore(inicio) && !dataHora.isAfter(fim);
                })
                .filter(a -> estabelecimentoId == null || (a.getEstabelecimento() != null && estabelecimentoId.equals(a.getEstabelecimento().getId())))
                .filter(a -> medicoId == null || (a.getProfissional() != null && medicoId.equals(a.getProfissional().getId())))
                .filter(a -> a.getProcedimentos() != null && !a.getProcedimentos().isEmpty())
                .mapToLong(a -> a.getProcedimentos().size())
                .sum();
    }

    private Map<String, Long> calcularAtendimentosPorTipo(UUID tenantId, OffsetDateTime inicio, OffsetDateTime fim, UUID estabelecimentoId, UUID medicoId) {
        Map<String, Long> porTipo = new HashMap<>();
        
        atendimentoRepository.findAllByTenant(tenantId, PageRequest.of(0, Integer.MAX_VALUE))
                .getContent().stream()
                .filter(a -> {
                    if (a.getInformacoes() == null || a.getInformacoes().getDataHora() == null) {
                        return false;
                    }
                    OffsetDateTime dataHora = a.getInformacoes().getDataHora();
                    return !dataHora.isBefore(inicio) && !dataHora.isAfter(fim);
                })
                .filter(a -> estabelecimentoId == null || (a.getEstabelecimento() != null && estabelecimentoId.equals(a.getEstabelecimento().getId())))
                .filter(a -> medicoId == null || (a.getProfissional() != null && medicoId.equals(a.getProfissional().getId())))
                .forEach(a -> {
                    String tipo = a.getInformacoes() != null && a.getInformacoes().getTipoAtendimento() != null
                            ? a.getInformacoes().getTipoAtendimento().name()
                            : "OUTRO";
                    porTipo.put(tipo, porTipo.getOrDefault(tipo, 0L) + 1);
                });
        
        return porTipo;
    }

    private Map<String, Long> calcularAtendimentosPorEspecialidade(UUID tenantId, OffsetDateTime inicio, OffsetDateTime fim, UUID estabelecimentoId, UUID medicoId, UUID especialidadeId) {
        Map<String, Long> porEspecialidade = new HashMap<>();
        
        agendamentoRepository.findByDataHoraBetweenAndTenantIdOrderByDataHoraAsc(
                inicio, fim, tenantId, PageRequest.of(0, Integer.MAX_VALUE))
                .getContent().stream()
                .filter(a -> estabelecimentoId == null || (a.getEstabelecimento() != null && estabelecimentoId.equals(a.getEstabelecimento().getId())))
                .filter(a -> medicoId == null || (a.getMedico() != null && medicoId.equals(a.getMedico().getId())))
                .filter(a -> especialidadeId == null || (a.getEspecialidade() != null && especialidadeId.equals(a.getEspecialidade().getId())))
                .forEach(a -> {
                    String especialidadeNome = a.getEspecialidade() != null && a.getEspecialidade().getNome() != null
                            ? a.getEspecialidade().getNome()
                            : "SEM ESPECIALIDADE";
                    porEspecialidade.put(especialidadeNome, porEspecialidade.getOrDefault(especialidadeNome, 0L) + 1);
                });
        
        return porEspecialidade;
    }

    private Map<String, BigDecimal> calcularIndicadores(long totalAtendimentos, long totalConsultas, long totalAgendamentos, long totalPacientes, OffsetDateTime inicio, OffsetDateTime fim) {
        Map<String, BigDecimal> indicadores = new HashMap<>();
        
        long dias = java.time.temporal.ChronoUnit.DAYS.between(inicio.toLocalDate(), fim.toLocalDate()) + 1;
        if (dias > 0) {
            BigDecimal mediaAtendimentosDia = BigDecimal.valueOf(totalAtendimentos)
                    .divide(BigDecimal.valueOf(dias), 2, RoundingMode.HALF_UP);
            indicadores.put("mediaAtendimentosPorDia", mediaAtendimentosDia);
            
            BigDecimal mediaConsultasDia = BigDecimal.valueOf(totalConsultas)
                    .divide(BigDecimal.valueOf(dias), 2, RoundingMode.HALF_UP);
            indicadores.put("mediaConsultasPorDia", mediaConsultasDia);
        }
        
        if (totalAgendamentos > 0) {
            BigDecimal taxaOcupacao = BigDecimal.valueOf(totalConsultas)
                    .divide(BigDecimal.valueOf(totalAgendamentos), 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
            indicadores.put("taxaOcupacaoAgendamentos", taxaOcupacao);
        }
        
        if (totalPacientes > 0) {
            BigDecimal atendimentosPorPaciente = BigDecimal.valueOf(totalAtendimentos)
                    .divide(BigDecimal.valueOf(totalPacientes), 2, RoundingMode.HALF_UP);
            indicadores.put("atendimentosPorPaciente", atendimentosPorPaciente);
        }
        
        return indicadores;
    }

    private RelatorioComparativoResponse.Comparacoes calcularComparacoes(
            RelatorioComparativoResponse.DadosPeriodo atual,
            RelatorioComparativoResponse.DadosPeriodo anterior) {

        // Calcular variações percentuais
        BigDecimal variacaoAtendimentos = calcularVariacao(atual.getTotalAtendimentos(), anterior.getTotalAtendimentos());
        BigDecimal variacaoConsultas = calcularVariacao(atual.getTotalConsultas(), anterior.getTotalConsultas());
        BigDecimal variacaoAgendamentos = calcularVariacao(atual.getTotalAgendamentos(), anterior.getTotalAgendamentos());
        BigDecimal variacaoPacientes = calcularVariacao(atual.getTotalPacientes(), anterior.getTotalPacientes());
        BigDecimal variacaoProcedimentos = calcularVariacao(atual.getTotalProcedimentos(), anterior.getTotalProcedimentos());
        BigDecimal variacaoValor = calcularVariacao(atual.getValorTotal(), anterior.getValorTotal());

        // Calcular diferenças absolutas
        long diferencaAtendimentos = atual.getTotalAtendimentos() - anterior.getTotalAtendimentos();
        long diferencaConsultas = atual.getTotalConsultas() - anterior.getTotalConsultas();
        long diferencaAgendamentos = atual.getTotalAgendamentos() - anterior.getTotalAgendamentos();
        long diferencaPacientes = atual.getTotalPacientes() - anterior.getTotalPacientes();
        long diferencaProcedimentos = atual.getTotalProcedimentos() - anterior.getTotalProcedimentos();
        BigDecimal diferencaValor = atual.getValorTotal().subtract(anterior.getValorTotal());

        // Determinar tendências
        RelatorioComparativoResponse.Tendencia tendenciaAtendimentos = determinarTendencia(variacaoAtendimentos);
        RelatorioComparativoResponse.Tendencia tendenciaConsultas = determinarTendencia(variacaoConsultas);
        RelatorioComparativoResponse.Tendencia tendenciaAgendamentos = determinarTendencia(variacaoAgendamentos);
        RelatorioComparativoResponse.Tendencia tendenciaValor = determinarTendencia(variacaoValor);

        // Comparações por tipo
        List<RelatorioComparativoResponse.ComparacaoItem> comparacaoPorTipo = calcularComparacaoPorItem(
                atual.getAtendimentosPorTipo(), anterior.getAtendimentosPorTipo());

        // Comparações por especialidade
        List<RelatorioComparativoResponse.ComparacaoItem> comparacaoPorEspecialidade = calcularComparacaoPorItem(
                atual.getAtendimentosPorEspecialidade(), anterior.getAtendimentosPorEspecialidade());

        return RelatorioComparativoResponse.Comparacoes.builder()
                .variacaoAtendimentos(variacaoAtendimentos)
                .variacaoConsultas(variacaoConsultas)
                .variacaoAgendamentos(variacaoAgendamentos)
                .variacaoPacientes(variacaoPacientes)
                .variacaoProcedimentos(variacaoProcedimentos)
                .variacaoValor(variacaoValor)
                .diferencaAtendimentos(diferencaAtendimentos)
                .diferencaConsultas(diferencaConsultas)
                .diferencaAgendamentos(diferencaAgendamentos)
                .diferencaPacientes(diferencaPacientes)
                .diferencaProcedimentos(diferencaProcedimentos)
                .diferencaValor(diferencaValor)
                .tendenciaAtendimentos(tendenciaAtendimentos)
                .tendenciaConsultas(tendenciaConsultas)
                .tendenciaAgendamentos(tendenciaAgendamentos)
                .tendenciaValor(tendenciaValor)
                .comparacaoPorTipo(comparacaoPorTipo)
                .comparacaoPorEspecialidade(comparacaoPorEspecialidade)
                .build();
    }

    private BigDecimal calcularVariacao(long valorAnterior, long valorAtual) {
        if (valorAnterior == 0) {
            return valorAtual > 0 ? BigDecimal.valueOf(100) : BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(valorAtual - valorAnterior)
                .divide(BigDecimal.valueOf(valorAnterior), 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    private BigDecimal calcularVariacao(BigDecimal valorAnterior, BigDecimal valorAtual) {
        if (valorAnterior == null || valorAnterior.compareTo(BigDecimal.ZERO) == 0) {
            return valorAtual != null && valorAtual.compareTo(BigDecimal.ZERO) > 0 
                    ? BigDecimal.valueOf(100) 
                    : BigDecimal.ZERO;
        }
        if (valorAtual == null) {
            return BigDecimal.ZERO;
        }
        return valorAtual.subtract(valorAnterior)
                .divide(valorAnterior, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    private RelatorioComparativoResponse.Tendencia determinarTendencia(BigDecimal variacao) {
        if (variacao == null) {
            return RelatorioComparativoResponse.Tendencia.ESTABILIDADE;
        }
        BigDecimal threshold = BigDecimal.valueOf(5); // 5% de variação para considerar significativa
        if (variacao.compareTo(threshold) > 0) {
            return RelatorioComparativoResponse.Tendencia.CRESCIMENTO;
        } else if (variacao.compareTo(threshold.negate()) < 0) {
            return RelatorioComparativoResponse.Tendencia.DECLINIO;
        } else {
            return RelatorioComparativoResponse.Tendencia.ESTABILIDADE;
        }
    }

    private List<RelatorioComparativoResponse.ComparacaoItem> calcularComparacaoPorItem(
            Map<String, Long> atual, Map<String, Long> anterior) {
        
        List<RelatorioComparativoResponse.ComparacaoItem> items = new ArrayList<>();
        
        // Coletar todas as chaves de ambos os períodos
        java.util.Set<String> todasChaves = new java.util.HashSet<>();
        if (atual != null) todasChaves.addAll(atual.keySet());
        if (anterior != null) todasChaves.addAll(anterior.keySet());
        
        for (String chave : todasChaves) {
            long valorAtual = atual != null ? atual.getOrDefault(chave, 0L) : 0L;
            long valorAnterior = anterior != null ? anterior.getOrDefault(chave, 0L) : 0L;
            
            BigDecimal variacao = calcularVariacao(valorAnterior, valorAtual);
            RelatorioComparativoResponse.Tendencia tendencia = determinarTendencia(variacao);
            
            items.add(RelatorioComparativoResponse.ComparacaoItem.builder()
                    .nome(chave)
                    .valorAtual(valorAtual)
                    .valorAnterior(valorAnterior)
                    .variacaoPercentual(variacao)
                    .tendencia(tendencia)
                    .build());
        }
        
        // Ordenar por variação percentual (maior variação primeiro)
        return items.stream()
                .sorted((a, b) -> b.getVariacaoPercentual().compareTo(a.getVariacaoPercentual()))
                .collect(Collectors.toList());
    }

    private RelatorioComparativoResponse.TipoComparacao convertTipoComparacao(RelatorioComparativoRequest.TipoComparacao tipo) {
        if (tipo == null) {
            return RelatorioComparativoResponse.TipoComparacao.PERIODO_TEMPORAL;
        }
        return switch (tipo) {
            case PERIODO_TEMPORAL -> RelatorioComparativoResponse.TipoComparacao.PERIODO_TEMPORAL;
            case ESTABELECIMENTO -> RelatorioComparativoResponse.TipoComparacao.ESTABELECIMENTO;
            case MEDICO -> RelatorioComparativoResponse.TipoComparacao.MEDICO;
            case ESPECIALIDADE -> RelatorioComparativoResponse.TipoComparacao.ESPECIALIDADE;
        };
    }
}
