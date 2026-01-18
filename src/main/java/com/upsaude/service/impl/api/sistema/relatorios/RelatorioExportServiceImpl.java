package com.upsaude.service.impl.api.sistema.relatorios;

import com.upsaude.api.request.sistema.relatorios.RelatorioExportRequest;
import com.upsaude.api.response.sia.kpi.SiaPaKpiResponse;
import com.upsaude.api.response.sia.relatorios.SiaPaRelatorioProducaoResponse;
import com.upsaude.api.response.sia.relatorios.SiaPaRelatorioTopCidResponse;
import com.upsaude.api.response.sia.relatorios.SiaPaRelatorioTopProcedimentosResponse;
import com.upsaude.api.response.sistema.dashboard.DashboardEstabelecimentoResponse;
import com.upsaude.api.response.sistema.dashboard.DashboardMedicoResponse;
import com.upsaude.api.response.sistema.dashboard.DashboardTenantResponse;
import com.upsaude.api.response.sistema.relatorios.RelatorioComparativoResponse;
import com.upsaude.api.response.sistema.relatorios.RelatorioEstatisticasResponse;
import com.upsaude.exception.BadRequestException;
import com.upsaude.service.api.sia.SiaPaKpiService;
import com.upsaude.service.api.sia.SiaPaRelatorioService;
import com.upsaude.service.api.sistema.dashboard.DashboardService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.sistema.relatorios.RelatorioComparativoService;
import com.upsaude.service.api.sistema.relatorios.RelatorioExportService;
import com.upsaude.service.api.sistema.relatorios.RelatoriosService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RelatorioExportServiceImpl implements RelatorioExportService {

    private final RelatoriosService relatoriosService;
    private final RelatorioComparativoService relatorioComparativoService;
    private final DashboardService dashboardService;
    private final SiaPaKpiService siaPaKpiService;
    private final SiaPaRelatorioService siaPaRelatorioService;
    private final TenantService tenantService;

    @Override
    public Resource exportarRelatorio(RelatorioExportRequest request) {
        log.debug("Exportando relatório. Tipo: {}, Formato: {}", request.getTipoRelatorio(), request.getFormatoExportacao());

        try {
            Path arquivoTemp = gerarArquivoTemporario(request);
            try {
                return new UrlResource(arquivoTemp.toUri().toURL());
            } catch (MalformedURLException e) {
                throw new RuntimeException("Erro ao criar URL do arquivo temporário", e);
            }
        } catch (Exception e) {
            log.error("Erro ao exportar relatório: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao exportar relatório: " + e.getMessage(), e);
        }
    }

    private Path gerarArquivoTemporario(RelatorioExportRequest request) throws IOException {
        UUID tenantId = tenantService.validarTenantAtual();
        String nomeArquivo = gerarNomeArquivo(request, tenantId);
        Path arquivoTemp = Files.createTempFile("relatorio_", nomeArquivo);

        switch (request.getFormatoExportacao()) {
            case PDF:
                gerarPDF(request, arquivoTemp);
                break;
            case EXCEL:
                gerarExcel(request, arquivoTemp);
                break;
            case CSV:
                gerarCSV(request, arquivoTemp);
                break;
            default:
                throw new BadRequestException("Formato de exportação não suportado: " + request.getFormatoExportacao());
        }

        return arquivoTemp;
    }

    private String gerarNomeArquivo(RelatorioExportRequest request, UUID tenantId) {
        String timestamp = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String extensao = getExtensao(request.getFormatoExportacao());
        return String.format("%s_%s_%s%s", 
                request.getTipoRelatorio().name().toLowerCase(),
                tenantId.toString().substring(0, 8),
                timestamp,
                extensao);
    }

    private String getExtensao(RelatorioExportRequest.FormatoExportacao formato) {
        return switch (formato) {
            case PDF -> ".pdf";
            case EXCEL -> ".xlsx";
            case CSV -> ".csv";
        };
    }

    private void gerarPDF(RelatorioExportRequest request, Path arquivo) throws IOException {
        
        
        log.warn("Geração de PDF ainda não implementada. Gerando CSV temporariamente.");
        gerarCSV(request, arquivo);
    }

    private void gerarExcel(RelatorioExportRequest request, Path arquivo) throws IOException {
        
        
        log.warn("Geração de Excel ainda não implementada. Gerando CSV temporariamente.");
        gerarCSV(request, arquivo);
    }

    private void gerarCSV(RelatorioExportRequest request, Path arquivo) throws IOException {
        try (FileWriter writer = new FileWriter(arquivo.toFile())) {
            switch (request.getTipoRelatorio()) {
                case ESTATISTICAS:
                    gerarCSVEstatisticas(request, writer);
                    break;
                case COMPARATIVO:
                    gerarCSVComparativo(request, writer);
                    break;
                case DASHBOARD_TENANT:
                    gerarCSVDashboardTenant(request, writer);
                    break;
                case DASHBOARD_ESTABELECIMENTO:
                    gerarCSVDashboardEstabelecimento(request, writer);
                    break;
                case DASHBOARD_MEDICO:
                    gerarCSVDashboardMedico(request, writer);
                    break;
                case SIA_KPI_GERAL:
                    gerarCSVSiaKpiGeral(request, writer);
                    break;
                case SIA_KPI_ESTABELECIMENTO:
                    gerarCSVSiaKpiEstabelecimento(request, writer);
                    break;
                case SIA_RELATORIO_PRODUCAO_MENSAL:
                    gerarCSVSiaProducaoMensal(request, writer);
                    break;
                case SIA_RELATORIO_TOP_PROCEDIMENTOS:
                    gerarCSVSiaTopProcedimentos(request, writer);
                    break;
                case SIA_RELATORIO_TOP_CID:
                    gerarCSVSiaTopCid(request, writer);
                    break;
                default:
                    throw new BadRequestException("Tipo de relatório não suportado: " + request.getTipoRelatorio());
            }
        }
    }

    private void gerarCSVEstatisticas(RelatorioExportRequest request, FileWriter writer) throws IOException {
        if (request.getRelatorioEstatisticas() == null) {
            throw new BadRequestException("Dados do relatório de estatísticas são obrigatórios");
        }

        RelatorioEstatisticasResponse response = relatoriosService.gerarEstatisticas(request.getRelatorioEstatisticas());

        writer.append("Relatório de Estatísticas\n");
        writer.append("Período: ").append(response.getDataInicio().toString())
                .append(" a ").append(response.getDataFim().toString()).append("\n\n");

        writer.append("Métrica,Valor\n");
        writer.append("Total de Atendimentos,").append(String.valueOf(response.getTotalAtendimentos())).append("\n");
        writer.append("Total de Consultas,").append(String.valueOf(response.getTotalConsultas())).append("\n");
        writer.append("Total de Agendamentos,").append(String.valueOf(response.getTotalAgendamentos())).append("\n");
        writer.append("Total de Pacientes,").append(String.valueOf(response.getTotalPacientes())).append("\n");
    }

    private void gerarCSVComparativo(RelatorioExportRequest request, FileWriter writer) throws IOException {
        if (request.getRelatorioComparativo() == null) {
            throw new BadRequestException("Dados do relatório comparativo são obrigatórios");
        }

        RelatorioComparativoResponse response = relatorioComparativoService.gerarRelatorioComparativo(request.getRelatorioComparativo());

        writer.append("Relatório Comparativo\n");
        writer.append("Período Atual: ").append(response.getPeriodoAtualInicio().toString())
                .append(" a ").append(response.getPeriodoAtualFim().toString()).append("\n");
        writer.append("Período Anterior: ").append(response.getPeriodoAnteriorInicio().toString())
                .append(" a ").append(response.getPeriodoAnteriorFim().toString()).append("\n\n");

        writer.append("Métrica,Período Atual,Período Anterior,Variação %,Diferença\n");
        writer.append("Atendimentos,")
                .append(String.valueOf(response.getPeriodoAtual().getTotalAtendimentos())).append(",")
                .append(String.valueOf(response.getPeriodoAnterior().getTotalAtendimentos())).append(",")
                .append(response.getComparacoes().getVariacaoAtendimentos() != null 
                        ? response.getComparacoes().getVariacaoAtendimentos().toString() : "0").append(",")
                .append(String.valueOf(response.getComparacoes().getDiferencaAtendimentos())).append("\n");
    }

    private void gerarCSVDashboardTenant(RelatorioExportRequest request, FileWriter writer) throws IOException {
        UUID tenantId = tenantService.validarTenantAtual();
        LocalDate dataInicio = LocalDate.now().minusMonths(1);
        LocalDate dataFim = LocalDate.now();

        DashboardTenantResponse response = dashboardService.dashboardTenant(tenantId, dataInicio, dataFim);

        writer.append("Dashboard Tenant\n");
        writer.append("Período: ").append(dataInicio.toString())
                .append(" a ").append(dataFim.toString()).append("\n\n");

        writer.append("Métrica,Valor\n");
        writer.append("Total de Atendimentos,").append(String.valueOf(response.getTotalAtendimentos())).append("\n");
        writer.append("Total de Consultas,").append(String.valueOf(response.getTotalConsultas())).append("\n");
        writer.append("Total de Agendamentos,").append(String.valueOf(response.getTotalAgendamentos())).append("\n");
    }

    private void gerarCSVDashboardEstabelecimento(RelatorioExportRequest request, FileWriter writer) throws IOException {
        if (request.getEstabelecimentoId() == null) {
            throw new BadRequestException("ID do estabelecimento é obrigatório");
        }

        LocalDate dataInicio = LocalDate.now().minusMonths(1);
        LocalDate dataFim = LocalDate.now();

        DashboardEstabelecimentoResponse response = dashboardService.dashboardEstabelecimento(
                request.getEstabelecimentoId(), dataInicio, dataFim);

        writer.append("Dashboard Estabelecimento\n");
        writer.append("Estabelecimento ID: ").append(request.getEstabelecimentoId().toString()).append("\n");
        writer.append("Período: ").append(dataInicio.toString())
                .append(" a ").append(dataFim.toString()).append("\n\n");

        writer.append("Métrica,Valor\n");
        writer.append("Total de Atendimentos,").append(String.valueOf(response.getTotalAtendimentos())).append("\n");
        writer.append("Total de Consultas,").append(String.valueOf(response.getTotalConsultas())).append("\n");
    }

    private void gerarCSVDashboardMedico(RelatorioExportRequest request, FileWriter writer) throws IOException {
        if (request.getMedicoId() == null) {
            throw new BadRequestException("ID do médico é obrigatório");
        }

        LocalDate dataInicio = LocalDate.now().minusMonths(1);
        LocalDate dataFim = LocalDate.now();

        DashboardMedicoResponse response = dashboardService.dashboardMedico(
                request.getMedicoId(), dataInicio, dataFim);

        writer.append("Dashboard Médico\n");
        writer.append("Médico ID: ").append(request.getMedicoId().toString()).append("\n");
        writer.append("Período: ").append(dataInicio.toString())
                .append(" a ").append(dataFim.toString()).append("\n\n");

        writer.append("Métrica,Valor\n");
        writer.append("Total de Atendimentos,").append(String.valueOf(response.getTotalAtendimentos())).append("\n");
        writer.append("Total de Consultas,").append(String.valueOf(response.getTotalConsultas())).append("\n");
    }

    private void gerarCSVSiaKpiGeral(RelatorioExportRequest request, FileWriter writer) throws IOException {
        String competencia = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        String uf = "MG"; 

        SiaPaKpiResponse response = siaPaKpiService.kpiGeral(competencia, uf);

        writer.append("KPI SIA Geral\n");
        writer.append("Competência: ").append(competencia).append("\n");
        writer.append("UF: ").append(uf).append("\n\n");

        writer.append("Métrica,Valor\n");
        writer.append("Total de Registros,").append(String.valueOf(response.getTotalRegistros())).append("\n");
        writer.append("Procedimentos Únicos,").append(String.valueOf(response.getProcedimentosUnicos())).append("\n");
        writer.append("Estabelecimentos Únicos,").append(String.valueOf(response.getEstabelecimentosUnicos())).append("\n");
    }

    private void gerarCSVSiaKpiEstabelecimento(RelatorioExportRequest request, FileWriter writer) throws IOException {
        
        gerarCSVSiaKpiGeral(request, writer);
    }

    private void gerarCSVSiaProducaoMensal(RelatorioExportRequest request, FileWriter writer) throws IOException {
        String uf = "MG"; 
        String competenciaInicio = LocalDate.now().minusMonths(6).format(DateTimeFormatter.ofPattern("yyyyMM"));
        String competenciaFim = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));

        SiaPaRelatorioProducaoResponse response = siaPaRelatorioService.gerarRelatorioProducaoMensal(
                uf, competenciaInicio, competenciaFim);

        writer.append("Relatório SIA Produção Mensal\n");
        writer.append("Período: ").append(competenciaInicio)
                .append(" a ").append(competenciaFim).append("\n\n");

        writer.append("Competência,Quantidade Produzida,Valor Produzido,Valor Aprovado\n");
        if (response.getItens() != null) {
            for (SiaPaRelatorioProducaoResponse.ItemProducaoMensal item : response.getItens()) {
                writer.append(item.getCompetencia() != null ? item.getCompetencia() : "").append(",")
                        .append(String.valueOf(item.getQuantidadeProduzidaTotal() != null ? item.getQuantidadeProduzidaTotal() : 0)).append(",")
                        .append(item.getValorProduzidoTotal() != null ? item.getValorProduzidoTotal().toString() : "0").append(",")
                        .append(item.getValorAprovadoTotal() != null ? item.getValorAprovadoTotal().toString() : "0").append("\n");
            }
        }
    }

    private void gerarCSVSiaTopProcedimentos(RelatorioExportRequest request, FileWriter writer) throws IOException {
        String uf = "MG"; 
        String competencia = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));

        SiaPaRelatorioTopProcedimentosResponse response = siaPaRelatorioService.gerarTopProcedimentos(
                uf, competencia, 10);

        writer.append("Top Procedimentos SIA\n");
        writer.append("Competência: ").append(competencia).append("\n\n");

        writer.append("Código,Nome,Quantidade Produzida,Valor Aprovado\n");
        if (response.getItens() != null) {
            for (SiaPaRelatorioTopProcedimentosResponse.ItemTopProcedimento item : response.getItens()) {
                writer.append(item.getProcedimentoCodigo() != null ? item.getProcedimentoCodigo() : "").append(",")
                        .append(item.getProcedimentoNome() != null ? item.getProcedimentoNome() : "").append(",")
                        .append(String.valueOf(item.getQuantidadeProduzidaTotal() != null ? item.getQuantidadeProduzidaTotal() : 0)).append(",")
                        .append(item.getValorAprovadoTotal() != null ? item.getValorAprovadoTotal().toString() : "0").append("\n");
            }
        }
    }

    private void gerarCSVSiaTopCid(RelatorioExportRequest request, FileWriter writer) throws IOException {
        String uf = "MG"; 
        String competencia = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));

        SiaPaRelatorioTopCidResponse response = siaPaRelatorioService.gerarTopCid(
                uf, competencia, 10);

        writer.append("Top CID SIA\n");
        writer.append("Competência: ").append(competencia).append("\n\n");

        writer.append("CID,Descrição,Quantidade Produzida,Valor Aprovado\n");
        if (response.getItens() != null) {
            for (SiaPaRelatorioTopCidResponse.ItemTopCid item : response.getItens()) {
                writer.append(item.getCidPrincipalCodigo() != null ? item.getCidPrincipalCodigo() : "").append(",")
                        .append(item.getCidDescricao() != null ? item.getCidDescricao() : "").append(",")
                        .append(String.valueOf(item.getQuantidadeProduzidaTotal() != null ? item.getQuantidadeProduzidaTotal() : 0)).append(",")
                        .append(item.getValorAprovadoTotal() != null ? item.getValorAprovadoTotal().toString() : "0").append("\n");
            }
        }
    }
}
