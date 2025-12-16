import fs from "fs/promises";
import path from "path";
import { logger } from "../core/logger.js";
import { getMergedRulesSync, type MergedRules } from "../rules/mergedRules.js";

export class ControllerGenerator {
    private rules: MergedRules;
    private projectRoot: string;

    constructor(projectRoot: string) {
        this.projectRoot = projectRoot;
        this.rules = getMergedRulesSync(projectRoot);
    }

    /**
     * Gera um controller REST completo baseado no nome da entidade.
     */
    async generate(entityName: string): Promise<string> {
        const filePath = this.getFilePath(entityName);
        const route = this.toRoute(entityName);

        const content = this.buildController(entityName, route);

        await fs.writeFile(filePath, content);
        logger.success(`Controller criado: ${filePath}`);

        return `✔ Controller gerado para ${entityName}`;
    }

    // =====================================================
    // Controller template
    // =====================================================
    private buildController(entityName: string, route: string): string {
        return `package com.upsaude.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;

import com.upsaude.api.request.${entityName}Request;
import com.upsaude.api.response.${entityName}Response;
import com.upsaude.service.${entityName}Service;

import java.util.UUID;

/**
 * Controller responsável por expor as operações públicas da entidade ${entityName}.
 * Segue os padrões definidos em controller-patterns.yaml e architecture-rules.yaml.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/${route}")
@RequiredArgsConstructor
public class ${entityName}Controller {

    private final ${entityName}Service service;

    @PostMapping
    public ResponseEntity<${entityName}Response> criar(@RequestBody ${entityName}Request request) {
        log.debug("POST /${route} — Criando novo ${entityName}: {}", request);
        return ResponseEntity.ok(service.criar(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<${entityName}Response> obterPorId(@PathVariable UUID id) {
        log.debug("GET /${route}/{} — Buscando ${entityName}", id);
        return ResponseEntity.ok(service.obterPorId(id));
    }

    @GetMapping
    public ResponseEntity<?> listar(Pageable pageable) {
        log.debug("GET /${route} — Listando página {} tamanho {}", pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.ok(service.listar(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<${entityName}Response> atualizar(@PathVariable UUID id, @RequestBody ${entityName}Request request) {
        log.debug("PUT /${route}/{} — Atualizando ${entityName}: {}", id, request);
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        log.debug("DELETE /${route}/{} — Exclusão lógica de ${entityName}", id);
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
`;
    }

    // =====================================================
    // Helpers
    // =====================================================
    private getFilePath(entityName: string): string {
        return path.join(
            this.projectRoot,
            `src/main/java/com/upsaude/controller/${entityName}Controller.java`
        );
    }

    private toRoute(entityName: string): string {
        return entityName
            .replace(/([A-Z])/g, "-$1")
            .toLowerCase()
            .replace(/^-/, "");
    }
}
