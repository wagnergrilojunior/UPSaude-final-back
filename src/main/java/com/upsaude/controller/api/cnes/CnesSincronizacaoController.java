package com.upsaude.controller.api.cnes;

import com.upsaude.api.response.cnes.CnesHistoricoResponse;
import com.upsaude.api.response.cnes.CnesSincronizacaoResponse;
import com.upsaude.entity.cnes.CnesHistoricoEstabelecimento;
import com.upsaude.enums.StatusSincronizacaoEnum;
import com.upsaude.enums.TipoEntidadeCnesEnum;
import com.upsaude.repository.cnes.CnesHistoricoEstabelecimentoRepository;
import com.upsaude.service.api.cnes.CnesSincronizacaoService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/cnes/sincronizacoes")
@Tag(name = "CNES Sincronização - Consulta", description = "API para consulta de histórico e sincronizações CNES")
@RequiredArgsConstructor
@Slf4j
public class CnesSincronizacaoController {

    private final CnesSincronizacaoService sincronizacaoService;
    private final CnesHistoricoEstabelecimentoRepository historicoRepository;
    private final TenantService tenantService;

    @GetMapping
    @Operation(
            summary = "Listar sincronizações CNES",
            description = "Lista sincronizações CNES com filtros opcionais por tipo de entidade, status e período"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de sincronizações retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = CnesSincronizacaoResponse.class))),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Page<CnesSincronizacaoResponse>> listar(
            @Parameter(description = "Tipo de entidade (ESTABELECIMENTO, PROFISSIONAL, EQUIPE, etc)", example = "ESTABELECIMENTO")
            @RequestParam(required = false) TipoEntidadeCnesEnum tipoEntidade,
            @Parameter(description = "Status da sincronização (PENDENTE, PROCESSANDO, SUCESSO, ERRO)", example = "SUCESSO")
            @RequestParam(required = false) StatusSincronizacaoEnum status,
            @Parameter(description = "Data início (formato ISO 8601)", example = "2025-01-01T00:00:00Z")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dataInicio,
            @Parameter(description = "Data fim (formato ISO 8601)", example = "2025-01-31T23:59:59Z")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dataFim,
            Pageable pageable) {
        log.debug("REQUEST GET /v1/cnes/sincronizacoes - tipoEntidade: {}, status: {}, dataInicio: {}, dataFim: {}", 
                tipoEntidade, status, dataInicio, dataFim);
        try {
            Page<CnesSincronizacaoResponse> response = sincronizacaoService.listar(tipoEntidade, status, dataInicio, dataFim, pageable);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro ao listar sincronizações CNES", ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obter sincronização por ID",
            description = "Retorna detalhes de uma sincronização específica pelo ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sincronização encontrada",
                    content = @Content(schema = @Schema(implementation = CnesSincronizacaoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Sincronização não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<CnesSincronizacaoResponse> obterPorId(
            @Parameter(description = "ID da sincronização", required = true)
            @PathVariable UUID id) {
        log.debug("REQUEST GET /v1/cnes/sincronizacoes/{}", id);
        try {
            CnesSincronizacaoResponse response = sincronizacaoService.obterPorId(id);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro ao obter sincronização: {}", id, ex);
            throw ex;
        }
    }

    @GetMapping("/historico/estabelecimento/{estabelecimentoId}")
    @Operation(
            summary = "Consultar histórico de sincronização de estabelecimento",
            description = "Retorna o histórico de sincronizações de um estabelecimento por competência"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Histórico retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Estabelecimento não encontrado")
    })
    public ResponseEntity<List<CnesHistoricoResponse>> consultarHistoricoEstabelecimento(
            @Parameter(description = "ID do estabelecimento", required = true)
            @PathVariable UUID estabelecimentoId,
            @Parameter(description = "Competência no formato AAAAMM (opcional)", example = "202501")
            @RequestParam(required = false) String competencia) {
        log.debug("REQUEST GET /v1/cnes/sincronizacoes/historico/estabelecimento/{} - competencia: {}", 
                estabelecimentoId, competencia);
        try {
            UUID tenantId = tenantService.validarTenantAtual();
            
            List<CnesHistoricoEstabelecimento> historicos;
            if (competencia != null) {
                historicos = historicoRepository.findByEstabelecimentoIdAndCompetenciaAndTenant(
                        estabelecimentoId, competencia, tenantId)
                        .map(List::of)
                        .orElse(List.of());
            } else {
                historicos = historicoRepository.findByEstabelecimentoIdAndTenantOrderByDataSincronizacaoDesc(
                        estabelecimentoId, tenantId);
            }
            
            List<CnesHistoricoResponse> response = historicos.stream()
                    .map(this::toHistoricoResponse)
                    .collect(Collectors.toList());
            
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Erro ao consultar histórico de estabelecimento: {}", estabelecimentoId, ex);
            throw ex;
        }
    }

    private CnesHistoricoResponse toHistoricoResponse(CnesHistoricoEstabelecimento entity) {
        return CnesHistoricoResponse.builder()
                .id(entity.getId())
                .estabelecimentoId(entity.getEstabelecimento().getId())
                .competencia(entity.getCompetencia())
                .dadosJsonb(entity.getDadosJsonb())
                .dataSincronizacao(entity.getDataSincronizacao())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}

