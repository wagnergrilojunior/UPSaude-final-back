package com.upsaude.controller.api.clinica.prontuario;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upsaude.api.request.clinica.prontuario.DoencaPacienteRequest;
import com.upsaude.api.response.clinica.prontuario.DoencaPacienteResponse;
import com.upsaude.service.api.clinica.prontuario.DoencaPacienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/prontuario/diagnosticos")
@RequiredArgsConstructor
@Tag(name = "Prontuário - Diagnósticos", description = "Gerenciamento de diagnósticos e doenças dos pacientes")
public class DoencaPacienteController {

    private final DoencaPacienteService service;

    @PostMapping
    @Operation(summary = "Adicionar diagnóstico ao prontuário do paciente")
    public ResponseEntity<DoencaPacienteResponse> criar(@Valid @RequestBody DoencaPacienteRequest request) {
        log.info("Requisição POST /api/v1/prontuario/diagnosticos");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter detalhes de um diagnóstico cadastrado")
    public ResponseEntity<DoencaPacienteResponse> obterPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.obterPorId(id));
    }

    @GetMapping("/prontuario/{prontuarioId}")
    @Operation(summary = "Listar todos os diagnósticos de um prontuário")
    public ResponseEntity<Page<DoencaPacienteResponse>> listarPorProntuario(@PathVariable UUID prontuarioId,
            Pageable pageable) {
        return ResponseEntity.ok(service.listarPorProntuario(prontuarioId, pageable));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar informações de um diagnóstico")
    public ResponseEntity<DoencaPacienteResponse> atualizar(@PathVariable UUID id,
            @Valid @RequestBody DoencaPacienteRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover um diagnóstico do prontuário")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/inativar")
    @Operation(summary = "Inativar um diagnóstico (ex: doença resolvida)")
    public ResponseEntity<Void> inativar(@PathVariable UUID id) {
        service.inativar(id);
        return ResponseEntity.noContent().build();
    }
}
