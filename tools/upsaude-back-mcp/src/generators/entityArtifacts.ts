import { logger } from "../core/logger.js";
import fs from "fs/promises";
import path from "path";
import { getMergedRulesSync, type MergedRules } from "../rules/mergedRules.js";

export class EntityArtifactsGenerator {
    private rules: MergedRules;
    private projectRoot: string;

    constructor(projectRoot: string) {
        this.projectRoot = projectRoot;
        this.rules = getMergedRulesSync(projectRoot);
    }


    /**
     * Gera TODOS os artefatos necessários para uma entidade.
     *
     * /mcp generate_entity_artifacts entityName="Alergia"
     */
    async generate(entityName: string): Promise<string> {
        const log: string[] = [];
        log.push(`# 📦 Geração de Artefatos — ${entityName}`);

        // Criar pastas necessárias
        await this.ensureFolders();

        // Gerar cada artefato
        log.push(await this.generateEntity(entityName));
        log.push(await this.generateRequest(entityName));
        log.push(await this.generateResponse(entityName));
        log.push(await this.generateDTO(entityName));
        log.push(await this.generateRepository(entityName));
        log.push(await this.generateMapper(entityName));
        log.push(await this.generateServiceInterface(entityName));
        log.push(await this.generateServiceImpl(entityName));
        log.push(await this.generateController(entityName));

        log.push("\n✅ Artefatos gerados com sucesso!");

        return log.join("\n");
    }

    // =====================================================
    //  Criar pastas obrigatórias
    // =====================================================
    private async ensureFolders() {
        const folders = [
            "entity",
            "api/request",
            "api/response",
            "dto",
            "mapper",
            "repository",
            "service",
            "service/impl",
            "controller"
        ];

        for (const folder of folders) {
            const dir = path.join(this.projectRoot, "src/main/java/com/upsaude", folder);
            await fs.mkdir(dir, { recursive: true });
        }
    }

    // =====================================================
    //  1. ENTITY
    // =====================================================
    private async generateEntity(entityName: string): Promise<string> {
        const tableName = this.toTableName(entityName);

        const content = `package com.upsaude.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "${tableName}")
@Data
@EqualsAndHashCode(callSuper = true)
public class ${entityName} extends BaseEntity {

    // TODO: Adicionar campos seguindo domain-rules.yaml

}
`;

        const filePath = this.out("entity", `${entityName}.java`);
        await fs.writeFile(filePath, content);

        return `✔ Entity criada: ${filePath}`;
    }

    // =====================================================
    //  2. REQUEST
    // =====================================================
    private async generateRequest(entityName: string): Promise<string> {
        const content = `package com.upsaude.api.request;

import jakarta.validation.constraints.*;

public class ${entityName}Request {
    // TODO: Adicionar campos validados
}
`;

        const filePath = this.out("api/request", `${entityName}Request.java`);
        await fs.writeFile(filePath, content);

        return `✔ Request criada: ${filePath}`;
    }

    // =====================================================
    //  3. RESPONSE
    // =====================================================
    private async generateResponse(entityName: string): Promise<string> {
        const content = `package com.upsaude.api.response;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Data;

@Data
public class ${entityName}Response {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
}
`;

        const filePath = this.out("api/response", `${entityName}Response.java`);
        await fs.writeFile(filePath, content);

        return `✔ Response criada: ${filePath}`;
    }

    // =====================================================
    //  4. DTO
    // =====================================================
    private async generateDTO(entityName: string): Promise<string> {
        const content = `package com.upsaude.dto;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Data;

@Data
public class ${entityName}DTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
}
`;

        const filePath = this.out("dto", `${entityName}DTO.java`);
        await fs.writeFile(filePath, content);

        return `✔ DTO criada: ${filePath}`;
    }

    // =====================================================
    //  5. REPOSITORY
    // =====================================================
    private async generateRepository(entityName: string): Promise<string> {
        const content = `package com.upsaude.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.upsaude.entity.${entityName};
import java.util.UUID;

public interface ${entityName}Repository extends JpaRepository<${entityName}, UUID> {

    // TODO: duplicidade: existsByNome, etc

}
`;

        const filePath = this.out("repository", `${entityName}Repository.java`);
        await fs.writeFile(filePath, content);

        return `✔ Repository criado: ${filePath}`;
    }

    // =====================================================
    //  6. MAPPER
    // =====================================================
    private async generateMapper(entityName: string): Promise<string> {
        const content = `package com.upsaude.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.upsaude.entity.${entityName};
import com.upsaude.dto.${entityName}DTO;
import com.upsaude.api.request.${entityName}Request;
import com.upsaude.api.response.${entityName}Response;
import com.upsaude.mapper.config.MappingConfig;

@Mapper(config = MappingConfig.class)
public interface ${entityName}Mapper extends EntityMapper<${entityName}, ${entityName}DTO> {

    @Mapping(target = "active", ignore = true)
    ${entityName} toEntity(${entityName}DTO dto);

    ${entityName}DTO toDTO(${entityName} entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    ${entityName} fromRequest(${entityName}Request request);

    void updateFromRequest(${entityName}Request request, @MappingTarget ${entityName} entity);

    ${entityName}Response toResponse(${entityName} entity);
}
`;

        const filePath = this.out("mapper", `${entityName}Mapper.java`);
        await fs.writeFile(filePath, content);

        return `✔ Mapper criado: ${filePath}`;
    }

    // =====================================================
    //  7. SERVICE INTERFACE
    // =====================================================
    private async generateServiceInterface(entityName: string): Promise<string> {
        const content = `package com.upsaude.service;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.upsaude.api.request.${entityName}Request;
import com.upsaude.api.response.${entityName}Response;

public interface ${entityName}Service {

    ${entityName}Response criar(${entityName}Request request);

    ${entityName}Response obterPorId(UUID id);

    Page<${entityName}Response> listar(Pageable pageable);

    ${entityName}Response atualizar(UUID id, ${entityName}Request request);

    void excluir(UUID id);
}
`;

        const filePath = this.out("service", `${entityName}Service.java`);
        await fs.writeFile(filePath, content);

        return `✔ Service Interface criada: ${filePath}`;
    }

    // =====================================================
    //  8. SERVICE IMPL
    // =====================================================
    private async generateServiceImpl(entityName: string): Promise<string> {
        const content = `package com.upsaude.service.impl;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.api.request.${entityName}Request;
import com.upsaude.api.response.${entityName}Response;
import com.upsaude.entity.${entityName};
import com.upsaude.repository.${entityName}Repository;
import com.upsaude.mapper.${entityName}Mapper;
import com.upsaude.service.${entityName}Service;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ${entityName}ServiceImpl implements ${entityName}Service {

    private final ${entityName}Repository repository;
    private final ${entityName}Mapper mapper;

    @Override
    @Transactional
    public ${entityName}Response criar(${entityName}Request request) {
        if (request == null)
            throw new BadRequestException("Dados são obrigatórios");

        ${entityName} entity = mapper.fromRequest(request);
        entity.setActive(true);

        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public ${entityName}Response obterPorId(UUID id) {
        if (id == null)
            throw new BadRequestException("ID é obrigatório");

        ${entityName} entity = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("${entityName} não encontrado"));

        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<${entityName}Response> listar(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional
    public ${entityName}Response atualizar(UUID id, ${entityName}Request request) {
        if (request == null)
            throw new BadRequestException("Dados são obrigatórios");

        ${entityName} entity = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("${entityName} não encontrado"));

        mapper.updateFromRequest(request, entity);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        ${entityName} entity = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("${entityName} não encontrado"));

        entity.setActive(false);
        repository.save(entity);
    }
}
`;

        const filePath = this.out("service/impl", `${entityName}ServiceImpl.java`);
        await fs.writeFile(filePath, content);

        return `✔ ServiceImpl criado: ${filePath}`;
    }

    // =====================================================
    //  9. CONTROLLER
    // =====================================================
    private async generateController(entityName: string): Promise<string> {
        const route = entityName.toLowerCase();

        const content = `package com.upsaude.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;

import com.upsaude.api.request.${entityName}Request;
import com.upsaude.api.response.${entityName}Response;
import com.upsaude.service.${entityName}Service;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/${route}")
@RequiredArgsConstructor
public class ${entityName}Controller {

    private final ${entityName}Service service;

    @PostMapping
    public ResponseEntity<${entityName}Response> criar(@RequestBody ${entityName}Request request) {
        return ResponseEntity.ok(service.criar(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<${entityName}Response> obter(@PathVariable UUID id) {
        return ResponseEntity.ok(service.obterPorId(id));
    }

    @GetMapping
    public ResponseEntity<?> listar(Pageable pageable) {
        return ResponseEntity.ok(service.listar(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<${entityName}Response> atualizar(@PathVariable UUID id, @RequestBody ${entityName}Request request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
`;

        const filePath = this.out("controller", `${entityName}Controller.java`);
        await fs.writeFile(filePath, content);

        return `✔ Controller criado: ${filePath}`;
    }

    // =====================================================
    // UTILS
    // =====================================================

    private out(subPath: string, file: string): string {
        return path.join(this.projectRoot, `src/main/java/com/upsaude/${subPath}/${file}`);
    }

    private toTableName(entity: string): string {
        return (
            entity
                .replace(/([A-Z])/g, "_$1")
                .toLowerCase()
                .replace(/^_/, "") + "s"
        );
    }
}
