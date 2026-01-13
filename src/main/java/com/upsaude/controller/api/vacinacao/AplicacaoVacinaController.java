package com.upsaude.controller.api.vacinacao;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.upsaude.dto.vacinacao.AplicacaoVacinaRequest;
import com.upsaude.dto.vacinacao.AplicacaoVacinaResponse;
import com.upsaude.dto.vacinacao.ReacaoRequest;
import com.upsaude.service.vacinacao.AplicacaoVacinaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/vacinacao/aplicacoes")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Aplicações de Vacina", description = "Registro e consulta de aplicações de vacina")
public class AplicacaoVacinaController {

    private final AplicacaoVacinaService aplicacaoService;

    @PostMapping
    @Operation(summary = "Registrar nova aplicação de vacina")
    public ResponseEntity<AplicacaoVacinaResponse> criar(@Valid @RequestBody AplicacaoVacinaRequest request) {
        log.info("Registrando aplicação de vacina para paciente: {}", request.getPacienteId());
        AplicacaoVacinaResponse response = aplicacaoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar aplicação de vacina por ID")
    public ResponseEntity<AplicacaoVacinaResponse> buscarPorId(@PathVariable UUID id) {
        log.info("Buscando aplicação de vacina: {}", id);
        return ResponseEntity.ok(aplicacaoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar aplicação de vacina")
    public ResponseEntity<AplicacaoVacinaResponse> atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody AplicacaoVacinaRequest request) {
        log.info("Atualizando aplicação de vacina: {}", id);
        return ResponseEntity.ok(aplicacaoService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir aplicação de vacina (soft delete)")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        log.info("Excluindo aplicação de vacina: {}", id);
        aplicacaoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Listar aplicações de vacina de um paciente")
    public ResponseEntity<List<AplicacaoVacinaResponse>> listarPorPaciente(@PathVariable UUID pacienteId) {
        log.info("Listando aplicações do paciente: {}", pacienteId);
        return ResponseEntity.ok(aplicacaoService.listarPorPaciente(pacienteId));
    }

    @GetMapping("/paciente/{pacienteId}/imunobiologico/{imunobiologicoId}")
    @Operation(summary = "Listar histórico de um imunobiológico específico para um paciente")
    public ResponseEntity<List<AplicacaoVacinaResponse>> listarHistoricoPaciente(
            @PathVariable UUID pacienteId,
            @PathVariable UUID imunobiologicoId) {
        log.info("Listando histórico do paciente {} para imunobiológico {}", pacienteId, imunobiologicoId);
        return ResponseEntity.ok(aplicacaoService.listarHistoricoPaciente(pacienteId, imunobiologicoId));
    }

    @GetMapping("/periodo")
    @Operation(summary = "Listar aplicações por período")
    public ResponseEntity<List<AplicacaoVacinaResponse>> listarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime fim) {
        log.info("Listando aplicações no período: {} a {}", inicio, fim);
        return ResponseEntity.ok(aplicacaoService.listarPorPeriodo(inicio, fim));
    }

    @PostMapping("/{id}/reacao")
    @Operation(summary = "Registrar reação adversa para uma aplicação")
    public ResponseEntity<AplicacaoVacinaResponse> registrarReacao(
            @PathVariable UUID id,
            @Valid @RequestBody ReacaoRequest request) {
        log.info("Registrando reação adversa para aplicação: {}", id);
        return ResponseEntity.ok(aplicacaoService.registrarReacao(id, request));
    }

    @GetMapping("/paciente/{pacienteId}/carteira")
    @Operation(summary = "Consultar carteira de vacinação do paciente")
    public ResponseEntity<Map<String, Object>> consultarCarteira(@PathVariable UUID pacienteId) {
        log.info("Consultando carteira de vacinação do paciente: {}", pacienteId);

        List<AplicacaoVacinaResponse> aplicacoes = aplicacaoService.listarPorPaciente(pacienteId);
        long totalAplicacoes = aplicacaoService.contarPorPaciente(pacienteId);

        Map<String, Object> carteira = new HashMap<>();
        carteira.put("pacienteId", pacienteId);
        carteira.put("totalAplicacoes", totalAplicacoes);
        carteira.put("aplicacoes", aplicacoes);

        return ResponseEntity.ok(carteira);
    }

    @GetMapping("/paciente/{pacienteId}/resumo")
    @Operation(summary = "Resumo da carteira de vacinação do paciente")
    public ResponseEntity<Map<String, Object>> resumoCarteira(@PathVariable UUID pacienteId) {
        log.info("Resumo da carteira de vacinação do paciente: {}", pacienteId);

        long totalAplicacoes = aplicacaoService.contarPorPaciente(pacienteId);

        Map<String, Object> resumo = new HashMap<>();
        resumo.put("pacienteId", pacienteId);
        resumo.put("totalAplicacoes", totalAplicacoes);

        return ResponseEntity.ok(resumo);
    }
}
