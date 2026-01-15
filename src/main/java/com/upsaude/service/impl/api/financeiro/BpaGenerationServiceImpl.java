package com.upsaude.service.impl.api.financeiro;

import com.upsaude.api.response.financeiro.BpaConsolidadoDto;
import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.entity.clinica.atendimento.Atendimento;
import com.upsaude.entity.clinica.atendimento.AtendimentoProcedimento;
import com.upsaude.entity.faturamento.DocumentoFaturamento;
import com.upsaude.entity.faturamento.DocumentoFaturamentoItem;
import com.upsaude.entity.financeiro.CompetenciaFinanceira;
import com.upsaude.entity.financeiro.GuiaAtendimentoAmbulatorial;
import com.upsaude.entity.financeiro.ReservaOrcamentariaAssistencial;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.referencia.cid.Cid10Subcategorias;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.faturamento.DocumentoFaturamentoRepository;
import com.upsaude.repository.financeiro.CompetenciaFinanceiraRepository;
import com.upsaude.repository.financeiro.GuiaAtendimentoAmbulatorialRepository;
import com.upsaude.repository.financeiro.ReservaOrcamentariaAssistencialRepository;
import com.upsaude.repository.agendamento.AgendamentoRepository;
import com.upsaude.repository.clinica.atendimento.AtendimentoRepository;
import com.upsaude.repository.clinica.atendimento.AtendimentoProcedimentoRepository;
import com.upsaude.service.api.financeiro.BpaGenerationService;
import com.upsaude.util.bpa.BpaLayoutDefinition;
import com.upsaude.util.bpa.BpaLineBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BpaGenerationServiceImpl implements BpaGenerationService {

    private final CompetenciaFinanceiraRepository competenciaFinanceiraRepository;
    private final DocumentoFaturamentoRepository documentoFaturamentoRepository;
    private final ReservaOrcamentariaAssistencialRepository reservaRepository;
    private final GuiaAtendimentoAmbulatorialRepository guiaRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final AtendimentoRepository atendimentoRepository;
    private final AtendimentoProcedimentoRepository atendimentoProcedimentoRepository;

    @Override
    @Transactional(readOnly = true)
    public String gerarBpa(UUID competenciaId, UUID tenantId) {
        log.info("Iniciando geração de BPA para competência {} e tenant {}", competenciaId, tenantId);

        List<BpaConsolidadoDto> dadosConsolidados = consolidarDadosCompetencia(competenciaId, tenantId);

        if (dadosConsolidados.isEmpty()) {
            log.warn("Nenhum dado encontrado para gerar BPA. Competência: {}, Tenant: {}", competenciaId, tenantId);
            return "";
        }

        StringBuilder arquivoBpa = new StringBuilder();
        int numeroLinha = 1;

        for (BpaConsolidadoDto dto : dadosConsolidados) {
            String linha = formatarLinhaBpa(dto, numeroLinha);
            arquivoBpa.append(linha).append("\n");
            numeroLinha++;
        }

        log.info("BPA gerado com sucesso. Total de linhas: {}", dadosConsolidados.size());
        return arquivoBpa.toString();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BpaConsolidadoDto> consolidarDadosCompetencia(UUID competenciaId, UUID tenantId) {
        log.debug("Consolidando dados para competência {} e tenant {}", competenciaId, tenantId);

        CompetenciaFinanceira competencia = competenciaFinanceiraRepository.findByIdAndTenant(competenciaId, tenantId)
                .orElseThrow(() -> new NotFoundException("Competência financeira não encontrada: " + competenciaId));

String competenciaCodigo = competencia.getCodigo();

        List<BpaConsolidadoDto> dadosConsolidados = new ArrayList<>();

        dadosConsolidados.addAll(consolidarDocumentosFaturamento(competenciaId, tenantId, competenciaCodigo));

        dadosConsolidados.addAll(consolidarReservasConsumidas(competenciaId, tenantId, competenciaCodigo));

        dadosConsolidados.addAll(consolidarGuiasAmbulatoriais(competenciaId, tenantId, competenciaCodigo));

        dadosConsolidados.addAll(consolidarAgendamentosConfirmados(competenciaId, tenantId, competenciaCodigo));

        dadosConsolidados.addAll(consolidarAtendimentosConcluidos(competenciaId, tenantId, competenciaCodigo));

        log.info("Dados consolidados: {} registros", dadosConsolidados.size());
        return dadosConsolidados;
    }

    private List<BpaConsolidadoDto> consolidarDocumentosFaturamento(UUID competenciaId, UUID tenantId, String competenciaCodigo) {
        List<BpaConsolidadoDto> dados = new ArrayList<>();

        List<DocumentoFaturamento> documentos = documentoFaturamentoRepository
                .findByTipoAndCompetenciaAndStatus("BPA", competenciaId, "FECHADO", tenantId, PageRequest.of(0, 10000))
                .getContent();

        for (DocumentoFaturamento documento : documentos) {
            for (DocumentoFaturamentoItem item : documento.getItens()) {
                if (item.getSigtapProcedimento() == null) continue;

                BpaConsolidadoDto dto = BpaConsolidadoDto.builder()
                        .cnes(obterCnesEstabelecimento(documento))
                        .competencia(competenciaCodigo)
                        .procedimentoCodigo(item.getSigtapProcedimento().getCodigoOficial())
                        .procedimentoNome(item.getSigtapProcedimento().getNome())
                        .quantidade(item.getQuantidade() != null ? item.getQuantidade() : 1)
                        .valorUnitario(item.getValorUnitario())
                        .valorTotal(item.getValorTotal())
                        .dataAtendimento(documento.getEmitidoEm() != null ? documento.getEmitidoEm() : OffsetDateTime.now())
                        .origemTipo("DOCUMENTO_FATURAMENTO")
                        .origemId(documento.getId())
                        .agendamentoId(documento.getAgendamento() != null ? documento.getAgendamento().getId() : null)
                        .atendimentoId(documento.getAtendimento() != null ? documento.getAtendimento().getId() : null)
                        .build();

                if (documento.getAtendimento() != null && documento.getAtendimento().getPaciente() != null) {
                    preencherDadosPaciente(dto, documento.getAtendimento().getPaciente());
                } else if (documento.getAgendamento() != null && documento.getAgendamento().getPaciente() != null) {
                    preencherDadosPaciente(dto, documento.getAgendamento().getPaciente());
                }

                if (documento.getAtendimento() != null && documento.getAtendimento().getDiagnostico() != null) {
                    Cid10Subcategorias cid = documento.getAtendimento().getDiagnostico().getMainCid10();
                    if (cid != null) {
                        dto.setCidPrincipal(cid.getSubcat());
                    }
                }

                dados.add(dto);
            }
        }

        return dados;
    }

    private List<BpaConsolidadoDto> consolidarReservasConsumidas(UUID competenciaId, UUID tenantId, String competenciaCodigo) {
        List<BpaConsolidadoDto> dados = new ArrayList<>();

        List<ReservaOrcamentariaAssistencial> reservas = reservaRepository
                .findByCompetenciaAndStatus(competenciaId, "CONSUMIDA", tenantId, PageRequest.of(0, 10000))
                .getContent();

        for (ReservaOrcamentariaAssistencial reserva : reservas) {
            if (reserva.getAgendamento() == null) continue;

            Agendamento agendamento = reserva.getAgendamento();
            String procedimentoCodigo = "0000000000";

            BpaConsolidadoDto dto = BpaConsolidadoDto.builder()
                    .cnes(obterCnesDoAgendamento(agendamento))
                    .competencia(competenciaCodigo)
                    .procedimentoCodigo(procedimentoCodigo)
                    .quantidade(1)
                    .valorUnitario(reserva.getValorReservadoTotal())
                    .valorTotal(reserva.getValorReservadoTotal())
                    .dataAtendimento(agendamento.getDataHora())
                    .origemTipo("RESERVA_CONSUMIDA")
                    .origemId(reserva.getId())
                    .agendamentoId(agendamento.getId())
                    .atendimentoId(agendamento.getAtendimento() != null ? agendamento.getAtendimento().getId() : null)
                    .pacienteId(agendamento.getPaciente() != null ? agendamento.getPaciente().getId() : null)
                    .build();

            if (agendamento.getPaciente() != null) {
                preencherDadosPaciente(dto, agendamento.getPaciente());
            }

            dados.add(dto);
        }

        return dados;
    }

    private List<BpaConsolidadoDto> consolidarGuiasAmbulatoriais(UUID competenciaId, UUID tenantId, String competenciaCodigo) {
        List<BpaConsolidadoDto> dados = new ArrayList<>();

        List<GuiaAtendimentoAmbulatorial> guias = guiaRepository
                .findByCompetencia(competenciaId, tenantId, PageRequest.of(0, 10000))
                .getContent()
                .stream()
                .filter(g -> "EMITIDA".equals(g.getStatus()) || "INTEGRADA_BPA".equals(g.getStatus()))
                .collect(Collectors.toList());

        for (GuiaAtendimentoAmbulatorial guia : guias) {
            String cnes = obterCnesDoAgendamentoOuAtendimento(guia.getAgendamento(), guia.getAtendimento());

            BpaConsolidadoDto dto = BpaConsolidadoDto.builder()
                    .cnes(cnes)
                    .competencia(competenciaCodigo)
                    .procedimentoCodigo("0000000000")
                    .quantidade(1)
                    .dataAtendimento(guia.getEmitidaEm() != null ? guia.getEmitidaEm() : OffsetDateTime.now())
                    .origemTipo("GUIA")
                    .origemId(guia.getId())
                    .agendamentoId(guia.getAgendamento() != null ? guia.getAgendamento().getId() : null)
                    .atendimentoId(guia.getAtendimento() != null ? guia.getAtendimento().getId() : null)
                    .pacienteId(guia.getPaciente() != null ? guia.getPaciente().getId() : null)
                    .build();

            if (guia.getPaciente() != null) {
                preencherDadosPaciente(dto, guia.getPaciente());
            }

            dados.add(dto);
        }

        return dados;
    }

    private List<BpaConsolidadoDto> consolidarAgendamentosConfirmados(UUID competenciaId, UUID tenantId, String competenciaCodigo) {
        List<BpaConsolidadoDto> dados = new ArrayList<>();

        List<Agendamento> agendamentos = agendamentoRepository
                .findByCompetenciaFinanceira(competenciaId, tenantId, PageRequest.of(0, 10000))
                .getContent()
                .stream()
                .filter(a -> a.getStatus() != null && "CONFIRMADO".equalsIgnoreCase(a.getStatus().name()))
                .collect(Collectors.toList());

        for (Agendamento agendamento : agendamentos) {
            String cnes = obterCnesDoAgendamento(agendamento);

            BpaConsolidadoDto dto = BpaConsolidadoDto.builder()
                    .cnes(cnes)
                    .competencia(competenciaCodigo)
                    .procedimentoCodigo("0000000000")
                    .quantidade(1)
                    .valorUnitario(agendamento.getValorEstimadoTotal())
                    .valorTotal(agendamento.getValorEstimadoTotal())
                    .dataAtendimento(agendamento.getDataHora())
                    .origemTipo("AGENDAMENTO")
                    .origemId(agendamento.getId())
                    .agendamentoId(agendamento.getId())
                    .pacienteId(agendamento.getPaciente() != null ? agendamento.getPaciente().getId() : null)
                    .build();

            if (agendamento.getPaciente() != null) {
                preencherDadosPaciente(dto, agendamento.getPaciente());
            }

            dados.add(dto);
        }

        return dados;
    }

    private List<BpaConsolidadoDto> consolidarAtendimentosConcluidos(UUID competenciaId, UUID tenantId, String competenciaCodigo) {
        List<BpaConsolidadoDto> dados = new ArrayList<>();

        List<Atendimento> atendimentos = atendimentoRepository
                .findByTenantAndCompetenciaFinanceira(tenantId, competenciaId, PageRequest.of(0, 10000))
                .getContent()
                .stream()
                .filter(a -> a.getInformacoes() != null
                        && a.getInformacoes().getStatusAtendimento() != null
                        && "CONCLUIDO".equalsIgnoreCase(a.getInformacoes().getStatusAtendimento().name()))
                .collect(Collectors.toList());

        for (Atendimento atendimento : atendimentos) {
            List<AtendimentoProcedimento> procedimentos = atendimentoProcedimentoRepository
                    .findByAtendimento(atendimento.getId(), tenantId);

            if (procedimentos.isEmpty()) {
                BpaConsolidadoDto dto = criarDtoDeAtendimento(atendimento, competenciaCodigo, null);
                dados.add(dto);
            } else {
                for (AtendimentoProcedimento procedimento : procedimentos) {
                    BpaConsolidadoDto dto = criarDtoDeAtendimento(atendimento, competenciaCodigo, procedimento);
                    dados.add(dto);
                }
            }
        }

        return dados;
    }

    private BpaConsolidadoDto criarDtoDeAtendimento(Atendimento atendimento, String competenciaCodigo, AtendimentoProcedimento procedimento) {
        String cnes = obterCnesDoAtendimento(atendimento);
        String procedimentoCodigo = "0000000000";
        Integer quantidade = 1;
        BigDecimal valorUnitario = BigDecimal.ZERO;
        BigDecimal valorTotal = BigDecimal.ZERO;
        String cidPrincipal = null;
        String cboProfissional = null;
        String caraterAtendimento = null;

        if (procedimento != null) {
            if (procedimento.getSigtapProcedimento() != null) {
                procedimentoCodigo = procedimento.getSigtapProcedimento().getCodigoOficial();
            }
            quantidade = procedimento.getQuantidade() != null ? procedimento.getQuantidade() : 1;
            valorUnitario = procedimento.getValorUnitario() != null ? procedimento.getValorUnitario() : BigDecimal.ZERO;
            valorTotal = procedimento.getValorTotal() != null ? procedimento.getValorTotal() : BigDecimal.ZERO;
            cidPrincipal = procedimento.getCidPrincipalCodigo();
            cboProfissional = procedimento.getCboCodigo();
            caraterAtendimento = procedimento.getCaraterAtendimento();
            if (procedimento.getCnes() != null) {
                cnes = procedimento.getCnes();
            }
        }

        if (cidPrincipal == null && atendimento.getDiagnostico() != null
                && atendimento.getDiagnostico().getMainCid10() != null) {
            cidPrincipal = atendimento.getDiagnostico().getMainCid10().getSubcat();
        }

        if (cboProfissional == null && atendimento.getProfissional() != null
                && atendimento.getProfissional().getSigtapOcupacao() != null) {
            cboProfissional = atendimento.getProfissional().getSigtapOcupacao().getCodigoOficial();
        }

        BpaConsolidadoDto dto = BpaConsolidadoDto.builder()
                .cnes(cnes)
                .competencia(competenciaCodigo)
                .procedimentoCodigo(procedimentoCodigo)
                .cidPrincipal(cidPrincipal)
                .quantidade(quantidade)
                .valorUnitario(valorUnitario)
                .valorTotal(valorTotal)
                .dataAtendimento(atendimento.getInformacoes().getDataHora())
                .cboProfissional(cboProfissional)
                .caraterAtendimento(caraterAtendimento)
                .origemTipo("ATENDIMENTO")
                .origemId(atendimento.getId())
                .atendimentoId(atendimento.getId())
                .pacienteId(atendimento.getPaciente() != null ? atendimento.getPaciente().getId() : null)
                .build();

        if (atendimento.getPaciente() != null) {
            preencherDadosPaciente(dto, atendimento.getPaciente());
        }

        return dto;
    }

    private void preencherDadosPaciente(BpaConsolidadoDto dto, Paciente paciente) {
        if (paciente.getDataNascimento() != null) {
            int idade = calcularIdade(paciente.getDataNascimento());
            dto.setIdade(idade);
        }

        if (paciente.getSexo() != null) {
String sexo = paciente.getSexo().name().substring(0, 1);
            dto.setSexo(sexo);
        }

        if (paciente.getDadosSociodemograficos() != null
                && paciente.getDadosSociodemograficos().getMunicipioNascimentoIbge() != null) {
            dto.setMunicipioPacienteIbge(paciente.getDadosSociodemograficos().getMunicipioNascimentoIbge());
        } else if (paciente.getEnderecos() != null && !paciente.getEnderecos().isEmpty()) {
            var endereco = paciente.getEnderecos().get(0).getEndereco();
            if (endereco != null && endereco.getCodigoIbgeMunicipio() != null) {
                dto.setMunicipioPacienteIbge(endereco.getCodigoIbgeMunicipio());
            }
        }
    }

    private int calcularIdade(LocalDate dataNascimento) {
        if (dataNascimento == null) {
            return 0;
        }
        LocalDate hoje = LocalDate.now();
        int idade = hoje.getYear() - dataNascimento.getYear();
        if (hoje.getDayOfYear() < dataNascimento.getDayOfYear()) {
            idade--;
        }
        return idade;
    }

    private String obterCnesEstabelecimento(DocumentoFaturamento documento) {
        if (documento.getAtendimento() != null) {
            String cnes = obterCnesDoAtendimento(documento.getAtendimento());
            if (!"0000000".equals(cnes)) {
                return cnes;
            }
        }
        if (documento.getAgendamento() != null) {
            String cnes = obterCnesDoAgendamento(documento.getAgendamento());
            if (!"0000000".equals(cnes)) {
                return cnes;
            }
        }
        return "0000000";
    }

    private String obterCnesDoAgendamento(Agendamento agendamento) {
        if (agendamento.getProfissional() != null && agendamento.getProfissional().getEstabelecimento() != null) {
            String cnes = agendamento.getProfissional().getEstabelecimento().getDadosIdentificacao() != null
                    ? agendamento.getProfissional().getEstabelecimento().getDadosIdentificacao().getCnes()
                    : null;
            if (cnes != null && !cnes.isEmpty()) {
                return cnes;
            }
        }
        if (agendamento.getMedico() != null && agendamento.getMedico().getEstabelecimentos() != null
                && !agendamento.getMedico().getEstabelecimentos().isEmpty()) {
            var medicoEstabelecimento = agendamento.getMedico().getEstabelecimentos().get(0);
            if (medicoEstabelecimento.getEstabelecimento() != null
                    && medicoEstabelecimento.getEstabelecimento().getDadosIdentificacao() != null) {
                String cnes = medicoEstabelecimento.getEstabelecimento().getDadosIdentificacao().getCnes();
                if (cnes != null && !cnes.isEmpty()) {
                    return cnes;
                }
            }
        }
        return "0000000";
    }

    private String obterCnesDoAtendimento(Atendimento atendimento) {
        if (atendimento.getProfissional() != null && atendimento.getProfissional().getEstabelecimento() != null) {
            String cnes = atendimento.getProfissional().getEstabelecimento().getDadosIdentificacao() != null
                    ? atendimento.getProfissional().getEstabelecimento().getDadosIdentificacao().getCnes()
                    : null;
            if (cnes != null && !cnes.isEmpty()) {
                return cnes;
            }
        }
        return "0000000";
    }

    private String obterCnesDoAgendamentoOuAtendimento(Agendamento agendamento, Atendimento atendimento) {
        if (atendimento != null) {
            return obterCnesDoAtendimento(atendimento);
        }
        if (agendamento != null) {
            return obterCnesDoAgendamento(agendamento);
        }
        return "0000000";
    }

    private String formatarLinhaBpa(BpaConsolidadoDto dto, int numeroLinha) {
        BpaLineBuilder builder = new BpaLineBuilder();

        builder.setField(BpaLayoutDefinition.CNES, dto.getCnes() != null ? dto.getCnes() : "0000000")
                .setField(BpaLayoutDefinition.COMPETENCIA, dto.getCompetencia())
                .setField(BpaLayoutDefinition.PROCEDIMENTO_SIGTAP, dto.getProcedimentoCodigo() != null ? dto.getProcedimentoCodigo() : "0000000000")
                .setField(BpaLayoutDefinition.CID_PRINCIPAL, dto.getCidPrincipal() != null ? dto.getCidPrincipal() : "")
                .setNumericField(BpaLayoutDefinition.QUANTIDADE, dto.getQuantidade() != null ? dto.getQuantidade() : 1)
                .setDecimalField(BpaLayoutDefinition.VALOR_UNITARIO, dto.getValorUnitario())
                .setDecimalField(BpaLayoutDefinition.VALOR_TOTAL, dto.getValorTotal())
                .setDateField(BpaLayoutDefinition.DATA_ATENDIMENTO, dto.getDataAtendimento())
                .setField(BpaLayoutDefinition.CBO_PROFISSIONAL, dto.getCboProfissional() != null ? dto.getCboProfissional() : "")
                .setField(BpaLayoutDefinition.CARATER_ATENDIMENTO, dto.getCaraterAtendimento() != null ? dto.getCaraterAtendimento() : "")
                .setNumericField(BpaLayoutDefinition.IDADE, dto.getIdade())
                .setField(BpaLayoutDefinition.SEXO, dto.getSexo() != null ? dto.getSexo() : "")
                .setField(BpaLayoutDefinition.MUNICIPIO_PACIENTE, dto.getMunicipioPacienteIbge() != null ? dto.getMunicipioPacienteIbge() : "")
                .setField(BpaLayoutDefinition.TIPO_DOCUMENTO_ORIGEM, dto.getTipoDocumentoOrigem() != null ? dto.getTipoDocumentoOrigem() : "")
                .setField(BpaLayoutDefinition.NUMERO_DOCUMENTO_ORIGEM, dto.getNumeroDocumentoOrigem() != null ? dto.getNumeroDocumentoOrigem() : "")
                .setNumeroLinha(numeroLinha);

        return builder.build();
    }
}
